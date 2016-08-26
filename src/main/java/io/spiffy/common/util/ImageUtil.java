package io.spiffy.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import io.spiffy.common.api.media.dto.MediaType;

public class ImageUtil {

    private static final int MAX_FONT_SIZE = 48;
    private static final int BOTTOM_MARGIN = 10;
    private static final int TOP_MARGIN = 5;
    private static final int SIDE_MARGIN = 10;

    private static BufferedImage asImage(final byte[] bytes) throws IOException {
        final InputStream in = new ByteArrayInputStream(bytes);
        return ImageIO.read(in);
    }

    private static byte[] asBytes(final BufferedImage image, final MediaType type) throws IOException {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, type.getSubtype(), baos);
            baos.flush();
            return baos.toByteArray();
        }
    }

    public static byte[] thumbnail(final byte[] bytes, final MediaType type, final int size) throws IOException {
        final BufferedImage image = asImage(bytes);
        final BufferedImage thumbnail = Scalr.resize(image, size);

        return asBytes(thumbnail, type);
    }

    public static byte[] macro(final byte[] bytes, final MediaType type, final String top, final String bottom)
            throws IOException {
        final BufferedImage image = asImage(bytes);

        final Graphics2D graphics = (Graphics2D) image.getGraphics();
        final String captionTop = top.toUpperCase();
        final String captionBottom = bottom.toUpperCase();
        write(graphics, captionTop, image, true);
        write(graphics, captionBottom, image, false);

        return asBytes(image, type);
    }

    private static void write(final Graphics2D graphics, final String text, final BufferedImage image, final boolean top) {
        int height = 0;
        int fontSize = MAX_FONT_SIZE;
        final int maxCaptionHeight = image.getHeight() / 5;
        final int maxLineWidth = image.getWidth() - SIDE_MARGIN * 2;
        String formattedString = "";

        do {
            graphics.setFont(new Font("Impact", Font.PLAIN, fontSize));
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

            // first inject newlines into the text to wrap properly
            final StringBuilder sb = new StringBuilder();
            int left = 0;
            int right = text.length() - 1;
            while (left < right) {

                String substring = text.substring(left, right + 1);
                Rectangle2D stringBounds = graphics.getFontMetrics().getStringBounds(substring, graphics);
                while (stringBounds.getWidth() > maxLineWidth) {
                    // look for a space to break the line
                    boolean spaceFound = false;
                    for (int i = right; i > left; i--) {
                        if (text.charAt(i) == ' ') {
                            right = i - 1;
                            spaceFound = true;
                            break;
                        }
                    }
                    substring = text.substring(left, right + 1);
                    stringBounds = graphics.getFontMetrics().getStringBounds(substring, graphics);

                    // If we're down to a single word and we are still too wide,
                    // the font is just too big.
                    if (!spaceFound && stringBounds.getWidth() > maxLineWidth) {
                        break;
                    }
                }
                sb.append(substring).append("\n");
                left = right + 2;
                right = text.length() - 1;
            }

            formattedString = sb.toString();

            // now determine if this font size is too big for the allowed height
            height = 0;
            for (final String line : formattedString.split("\n")) {
                final Rectangle2D stringBounds = graphics.getFontMetrics().getStringBounds(line, graphics);
                height += stringBounds.getHeight();
            }
            fontSize--;
        } while (height > maxCaptionHeight);

        // draw the string one line at a time
        int y = 0;
        if (top) {
            y = TOP_MARGIN + graphics.getFontMetrics().getHeight();
        } else {
            y = image.getHeight() - height - BOTTOM_MARGIN + graphics.getFontMetrics().getHeight();
        }
        for (final String line : formattedString.split("\n")) {
            // Draw each string twice for a shadow effect
            final Rectangle2D stringBounds = graphics.getFontMetrics().getStringBounds(line, graphics);
            graphics.setColor(Color.BLACK);
            for (int i = -2; i <= 2; i++) {
                for (int j = -2; j <= 2; j++) {
                    graphics.drawString(line, (image.getWidth() - (int) stringBounds.getWidth()) / 2 + i, y + j);
                }
            }
            graphics.setColor(Color.WHITE);
            graphics.drawString(line, (image.getWidth() - (int) stringBounds.getWidth()) / 2, y);
            y += graphics.getFontMetrics().getHeight();
        }
    }
}
