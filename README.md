[![Gitter](https://img.shields.io/gitter/room/disneystreaming/weaver-test.svg)](https://gitter.im/disneystreaming/weaver-test)
[![CLA assistant](https://cla-assistant.io/readme/badge/disneystreaming/weaver-test)](https://cla-assistant.io/disneystreaming/weaver-test)


# weaver-intellij

A idea plugin for folks who like to run tests from the IDE.

### Instructions

To run the dev version of the plugin, the sequence of steps goes as follow (within sbt) :

* intellij/updateIntellij // downloads the intellij runtime (~1GB)
* intellij/clean // seems to be required after every change
* intellij/packagePlugin
* intellij/runIDE // launches intellij

### PR Guidelines

Please:
- Sign the CLA
- Write positive and negative tests
- Include documentation






