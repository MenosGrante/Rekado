> 🇺🇦 UKRAINE [IS BEING ATTACKED](https://war.ukraine.ua/) BY RUSSIAN ARMY. CIVILIANS ARE GETTING KILLED. RESIDENTIAL AREAS ARE GETTING BOMBED.
> - Help Ukraine via:
>   - [Serhiy Prytula Charity Foundation](https://prytulafoundation.org/en/)
>   - [Come Back Alive Charity Foundation](https://savelife.in.ua/en/donate-en/)
>   - [National Bank of Ukraine](https://bank.gov.ua/en/news/all/natsionalniy-bank-vidkriv-spetsrahunok-dlya-zboru-koshtiv-na-potrebi-armiyi)
> - More info on [war.ukraine.ua](https://war.ukraine.ua/) and [MFA of Ukraine](https://twitter.com/MFA_Ukraine)

<hr/>

<a href="https://www.buymeacoffee.com/pavlorekun" target="_blank"><img src="https://i.imgur.com/5PiChJC.png" alt="BMC" height="60"></a>

[![Crowdin](https://badges.crowdin.net/rekado/localized.svg)](https://crowdin.com/project/rekado) [![Stars](https://img.shields.io/github/stars/MenosGrante/Rekado)](https://github.com/MenosGrante/Rekado/stargazers) [![License](https://img.shields.io/github/license/MenosGrante/Rekado)](https://github.com/MenosGrante/Rekado/blob/master/LICENSE) [![Releases](https://img.shields.io/github/downloads/MenosGrante/Rekado/total.svg)](https://github.com/MenosGrante/Rekado/releases/latest)

# Rekado
Payload launcher written in Kotlin for Nintendo Switch.

**Application doesn't require Root on your device.**

[Fusee](https://github.com/Atmosphere-NX/Atmosphere) and [Hekate](https://github.com/CTCaer/hekate) payloads bundled as default.

## Usage
* Launch the application.
* Find a cable to connect your device to the Nintendo Switch. For proper work, this should be a cable that is designed for data transmission, not just for charging. It is advisable to use an **A-to-C** cable and **USB OTG** adapter.
* In the **"Payloads"** category, click the **"+"** button to select preloaded payload from your device's storage. Or simply transfer your payload to the Rekado folder in the device's memory. Or you can use the bundled payloads (**Hekate/Fusee**).
* Enter your Nintendo Switch into **RCM** mode in any convenient way. Your Nintendo Switch will power on by itself when plugged in, be sure to hold **VOLUME +**.
* Connect the device to the Nintendo Switch and allow permission for the **USB** access if necessary. Wait unit payloads chooser dialog will be opened and select which one you want to load.
* Wait for the payload to finish loading on your console.

## Download
You can get APK (installation file) from [Releases](https://github.com/MenosGrante/Rekado/releases) form in this repository.

## Known issues
**Nothing happens after connecting Nintendo Switch**

In most cases, this problem occurs if your device doesn't support OTG connection or it could be just disabled by default (e.g. OnePlus devices). Try to check if your device supports it and check if it is enabled and try again.

**Sending payload failed at offset**

This problem is in most cases not related to the used device, but the cable or adapter. It occurs most often due to cables that are not designed to transfer large amounts of data. Try to use another cable or adapter.

**SUBMITURB**

This problem occurs on a device with old USB controllers installed in their devices (EHCI/USB 2.0). This is a device-only problem, that can be fixed by installing additional kernel patches, which is not recommended to do yourself. Only devices with xHCI (USB 3.0) controllers are supported now.

## Localization
All localization files moved to Crowdin platform (which I am using for my other projects) and if you want to add/update/check any localization follow [this link](https://crowdin.com/project/rekado) to start. I will decline all pull requests with your localizations and will accept only Crowdin versions, which I will manually add in new updates.

## FAQ
**Does the application require Root?**

Rekado doesn't require Root on your device.

**Can it brick my device/console?**

This should not happen when using the "correct" payloads. But I am not responsible for possible problems.

## Credits
* [DavidBuchanan314](https://github.com/DavidBuchanan314) for creating NXLoader.
* [ealfonso93](https://github.com/ealfonso93) for contributing in this project.

## Donate
I have created and supporting this and other my apps in my free time, so if you would like to support me, check my <a href="https://www.buymeacoffee.com/pavlorekun" target="_blank">"Buy Me A Coffee"</a> page to support me and my projects.
