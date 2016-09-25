Spiffy = {}
Spiffy.constants = {}
Spiffy.c = Spiffy.constants

Spiffy.c.enum =
  loglevel:
    FATAL: 0
    ERROR: 1
    WARN: 2
    INFO: 3
    DEBUG: 4
    TRACE: 5

Spiffy.c.param =
  ATTEMPT: 1
  ETAG: undefined

Spiffy.c.timeout =
  RETRY: 5000

Spiffy.c.key =
  LEFT: 37
  RIGHT: 39

Spiffy.c.size = {}

Spiffy.c.size.width =
  XS: 320
  SM: 480
  MD: 768
  LG: 992
  XL: 120

Spiffy.c.config =
  CDN: '//cdn.spiffy.io'
  LOGLEVEL: Spiffy.c.enum.loglevel.TRACE
