Hipchat log appender
=============

Hipchat log appender is a logging facility for logback to bring logging directly from your (production)code to hipchat.
It is a standard logback appender, so easy to configure. Just like any other appender. 

Features
-------

* Standard (logback) logging
* Hipchat configuration (for your own environment)
* Configurable logging

Installation
-----------

To use the log appender the jar file should be added as runtime dependency in your project.

Prerequisites:
* Your project should use logback as logger.
* You have a hipchat account and admin key.

TODO
-----------

* Make this library available in a public central repository.
* Version numbering the output library.

DOCUMENTATION
--------------

HipchatLogAppender uses the rooms/message resource from the Hipchat API, https://www.hipchat.com/docs/api/method/rooms/message

