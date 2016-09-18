package io.spiffy.discussion.service;

import java.util.*;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.discussion.dto.MessengerMessage;
import io.spiffy.common.api.discussion.dto.MessengerThread;
import io.spiffy.common.api.discussion.dto.ThreadDTO;
import io.spiffy.common.api.discussion.output.CreateThreadOutput;
import io.spiffy.common.api.discussion.output.GetMessagesOutput;
import io.spiffy.common.api.discussion.output.GetThreadsOutput;
import io.spiffy.common.api.discussion.output.PostMessageOutput;
import io.spiffy.common.api.stream.client.StreamClient;
import io.spiffy.common.api.stream.dto.Post;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.dto.Account;
import io.spiffy.common.dto.EntityType;
import io.spiffy.common.util.DateUtil;
import io.spiffy.common.util.DurationUtil;
import io.spiffy.common.util.ObfuscateUtil;
import io.spiffy.discussion.entity.CommentEntity;
import io.spiffy.discussion.entity.ParticipantEntity;
import io.spiffy.discussion.entity.ThreadEntity;
import io.spiffy.discussion.manager.DiscussionSNSManager;
import io.spiffy.discussion.repository.ThreadRepository;

public class ThreadService extends Service<ThreadEntity, ThreadRepository> {

    private static final Map<String, String> ICONS;
    private static final String DEFAULT_ICON = "//cdn.spiffy.io/media/DxrwtJ-Cg.jpg";

    static {
        final Map<String, String> icons = new HashMap<>();
        icons.put("john", "//cdn.spiffy.io/media/MPMBQx-Cg.jpg");
        icons.put("johnrich", "//cdn.spiffy.io/media/CVQrJj-Cg.jpg");
        icons.put("maj", "//cdn.spiffy.io/media/MTdfxC-Cg.jpg");
        icons.put("cjsmile", "//cdn.spiffy.io/media/CnPfCX-Cg.jpg");
        icons.put("dadtv1234", "//cdn.spiffy.io/media/GQfhNV-Cg.jpg");

        ICONS = Collections.unmodifiableMap(icons);
    }

    private final CommentService commentService;
    private final ParticipantService participantService;
    private final DiscussionSNSManager snsManager;
    private final StreamClient streamClient;
    private final UserClient userClient;

    @Inject
    public ThreadService(final ThreadRepository repository, final CommentService commentService,
            final ParticipantService participantService, final DiscussionSNSManager snsManager, final StreamClient streamClient,
            final UserClient userClient) {
        super(repository);
        this.commentService = commentService;
        this.participantService = participantService;
        this.snsManager = snsManager;
        this.streamClient = streamClient;
        this.userClient = userClient;
    }

    @Transactional
    public ThreadEntity get(final long id) {
        return repository.get(id);
    }

    @Transactional
    public ThreadEntity get(final EntityType entityType, final String entityId) {
        return repository.get(entityType, entityId);
    }

    @Transactional
    public ThreadEntity get(final ThreadDTO thread) {
        if (thread.getId() != null) {
            return get(thread.getId());
        }

        return post(thread.getEntityType(), thread.getEntityId());
    }

    @Transactional
    public ThreadEntity post(final EntityType entityType, final String entityId) {
        ThreadEntity entity = get(entityType, entityId);
        if (entity == null) {
            entity = new ThreadEntity(entityType, entityId);
            repository.saveOrUpdate(entity);
        }

        return entity;
    }

    private String getThreadId(final long creatorAccountId, final Set<String> participants) {
        final Set<Long> accountIds = new HashSet<>();
        for (final String participant : participants) {
            final Account account = userClient.getAccount(participant);
            if (account == null) {
                continue;
            } else if (account.getId() == null) {
                continue;
            }

            accountIds.add(account.getId());
        }
        accountIds.add(creatorAccountId);

        final List<Long> ordered = new ArrayList<>(accountIds);
        ordered.sort((a, b) -> Long.compare(a, b));
        return StringUtils.join(ordered.toArray(), ",");
    }

    @Transactional
    public GetMessagesOutput getMessages(final ThreadDTO thread, final long accountId, final Set<String> participants,
            final String after) {
        thread.setEntityId(getThreadId(accountId, participants));
        final ThreadEntity entity = get(thread);

        final List<CommentEntity> entities = commentService.getMessages(entity, after);
        final List<MessengerMessage> messages = new ArrayList<>();

        for (final CommentEntity e : entities) {
            messages.add(new MessengerMessage(ObfuscateUtil.obfuscate(e.getId()),
                    e.getAccountId() == accountId ? "right" : "left", DEFAULT_ICON, e.getComment()));
        }

        return new GetMessagesOutput(messages);
    }

    @Transactional
    public PostMessageOutput postMessage(final ThreadDTO thread, final long accountId, final Set<String> participants,
            final String idempotentId, final String comment) {
        thread.setEntityId(getThreadId(accountId, participants));
        final ThreadEntity entity = get(thread);

        final CommentEntity e = commentService.post(entity, idempotentId, accountId, DateUtil.now(), comment);

        return new PostMessageOutput(new MessengerMessage(ObfuscateUtil.obfuscate(e.getId()),
                e.getAccountId() == accountId ? "right" : "left", DEFAULT_ICON, e.getComment()));
    }

    @Transactional
    public CreateThreadOutput createThread(final ThreadDTO thread, final long creatorAccountId,
            final Set<String> participants) {
        if (participants.size() < 1) {
            return new CreateThreadOutput(CreateThreadOutput.Error.TOO_FEW_NAMES);
        } else if (participants.size() > 9) {
            return new CreateThreadOutput(CreateThreadOutput.Error.TOO_MANY_NAMES);
        }

        final Set<Long> accountIds = new HashSet<>();
        for (final String participant : participants) {
            final Account account = userClient.getAccount(participant);
            if (account == null) {
                continue;
            } else if (account.getId() == null) {
                continue;
            }

            accountIds.add(account.getId());
        }

        if (accountIds.size() != participants.size()) {
            return new CreateThreadOutput(CreateThreadOutput.Error.UNKNOWN_NAME);
        }
        accountIds.add(creatorAccountId);
        if (accountIds.size() < 2) {
            return new CreateThreadOutput(CreateThreadOutput.Error.TOO_FEW_NAMES);
        }

        if (thread.getEntityId() == null) {
            final List<Long> ordered = new ArrayList<>(accountIds);
            ordered.sort((a, b) -> Long.compare(a, b));
            thread.setEntityId(StringUtils.join(ordered.toArray(), ","));
        }

        final ThreadEntity entity = post(thread.getEntityType(), thread.getEntityId());
        for (final Long accountId : accountIds) {
            participantService.post(entity, accountId);
        }

        return new CreateThreadOutput(getThread(entity, creatorAccountId));
    }

    public GetThreadsOutput getThreads(final long accountId) {
        final List<ParticipantEntity> entities = participantService.getByAccount(EntityType.MESSAGE, accountId);
        final List<MessengerThread> threads = new ArrayList<>();
        entities.forEach(e -> threads.add(getThread(e.getThread(), accountId)));
        return new GetThreadsOutput(threads);
    }

    private MessengerThread getThread(final ThreadEntity entity, final long accountId) {
        final List<ParticipantEntity> participants = participantService.getByThread(entity);

        final List<Account> accounts = new ArrayList<>();
        participants.forEach(p -> accounts.add(userClient.getAccount(p.getAccountId())));

        final List<String> names = new ArrayList<>();
        for (final Account account : accounts) {
            if (account == null) {
                continue;
            } else if (account.getId() == accountId) {
                continue;
            }

            names.add(account.getUsername());
        }

        final String id = StringUtils.join(names, ",");

        final CommentEntity comment = commentService.getMostRecent(entity);
        final String preview;
        final String time;
        if (comment == null) {
            preview = "";
            time = "New";
        } else {
            preview = comment.getComment();
            time = DurationUtil.pretty(comment.getPostedAt());
        }

        return MessengerThread.builder().id(id).icon(ICONS.getOrDefault(id, DEFAULT_ICON)).preview(preview).time(time).build();
    }

    @Transactional
    public List<CommentEntity> getComments(final ThreadDTO thread, final Long first, final int maxResults) {
        return commentService.get(get(thread), first, maxResults);
    }

    @Transactional
    public CommentEntity postComment(final ThreadDTO thread, final String idempotentId, final Long accountId,
            final String comment) {
        final ThreadEntity entity = get(thread);
        if (entity == null) {
            return null;
        }

        final CommentEntity commentEntity = commentService.post(entity, idempotentId, accountId, DateUtil.now(), comment);
        participantService.post(entity, accountId);
        snsManager.publish(commentEntity.getId());

        return commentEntity;
    }

    @Transactional
    public void sendNotifications(final long commentId) {
        final CommentEntity comment = commentService.get(commentId);

        final Set<Long> subscribers = commentService.getCommenters(comment.getThread());
        final Post post = streamClient.getPost(Long.parseLong(comment.getThread().getEntityId())).getPost();
        if (post != null) {
            subscribers.add(post.getAccount().getId());
        }
        subscribers.remove(comment.getAccountId());

        snsManager.publish(comment.getId(), Long.parseLong(comment.getThread().getEntityId()), subscribers);
    }
}
