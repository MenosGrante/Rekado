### The application has already received all the functionality that I planned to lay in it, so I will only fix defects and add localizations in the future. [More details here.](https://pavelrekun.dev/blog/news_25.08.2020/)


<a href="https://www.buymeacoffee.com/pavelrekun" target="_blank"><img src="https://i.imgur.com/yAojiF0.png" alt="Buy me a coffee" height="60"></a>

[![Crowdin](https://badges.crowdin.net/rekado/localized.svg)](https://crowdin.com/project/rekado) [![Stars](https://img.shields.io/github/stars/MenosGrante/Rekado)](https://github.com/MenosGrante/Rekado/stargazers) [![License](https://img.shields.io/github/license/MenosGrante/Rekado)](https://github.com/MenosGrante/Rekado/blob/master/LICENSE) [![Releases](https://img.shields.io/github/downloads/MenosGrante/Rekado/total.svg)](https://github.com/MenosGrante/Rekado/releases/latest)

# Rekado
Payload launcher written in Kotlin for Nintendo Switch.

**Application doesn't require Root on your device.**

[ReiNX](https://reinx.guide/), [Fusee](https://github.com/Atmosphere-NX/Atmosphere) and [Hekate](https://github.com/CTCaer/hekate) payloads bundled as default.

## Usage
* Launch application.
* Find a cable to connect your device to the Nintendo Switch. For proper work, this should be a cable that is designed for data transmission, not just for charging. It is advisable to use an **A-to-C** cable and an **USB OTG** adapter.
* In the **"Payloads"** category, click the **"+"** button to select preloaded payload from your device's storage. Or simply transfer your payload to the Rekado folder in the device's memory. Or you can use the bundled payloads (**ReiNX/Hekate/Fusee**).
* Enter your Nintendo Switch into **RCM** mode in any convenient way. Your Nintendo Switch will power on by itself when plugged in, be sure to hold **VOLUME +**.
* Connect the device to the Nintendo Switch and allow permission for the **USB** access if necessary. Wait unit payloads chooser dialog will be opened and select which one you want to load.
* Wait for payload to finish loading on your console.

## Download
You can get APK (installation file) from [Releases](https://github.com/MenosGrante/Rekado/releases) form in this repository.

## Known issues
**Nothing happens after connecting Nintendo Switch**

In most cases this problems occurs if your device doesn't not support OTG connection or it could be just disabled by default (e.g. OnePlus devices). Try to check if your device supports it and check if it is enabled and try again.

**Sending payload failed at offset**

This problem is in most cases not related to the used device, but to the cable or adapter. It occurs most often due to cables that are not designed to transfer large amounts of data. Try to use another cable or adapter.

**SUBMITURB**

This problems occurs on device with old USB-controllers installed in their devices (EHCI/USB 2.0). This is device-only problem, that can be fixed with installing additional kernel patches, what is not recommended to do yourself. Only devices with xHCI (USB 3.0) controllers supported now.

## Localization
All localization files moved to Crowdin platform (which I am using for my other projects) and if you want to add/update/check any localization follow [this link](https://crowdin.com/project/rekado) to start. I will decline all pull requests with your localizations and will accept only Crowdin versions, which I will manually add in new updates.

## FAQ
**Does application require Root?**

Application doesn't require Root on your device.

**Can it brick my device/console?**

This should not happen when using the "correct" payloads. But I am not responsible for possible problems.

## Credits
* [DavidBuchanan314](https://github.com/DavidBuchanan314) for creating NXLoader.
* [ealfonso93](https://github.com/ealfonso93) for contributing in this project.

## Donate
I have created and supporting this application in my free time, so if you want to support me, you can become my patron at <a href="https://www.patreon.com/bePatron?u=29011893" target="_blank">"Patreon"</a> and get exclusive news and early look at all my apps!
