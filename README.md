<a href="https://www.buymeacoffee.com/pavelrekun" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/yellow_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>

# Rekado
Payload launcher written in Kotlin for Nintendo Switch.

**Application doesn't require Root on your device.**

[SX Loader](https://sx.xecuter.com/), [ReiNX](https://reinx.guide/) and [Hekate](https://github.com/CTCaer/hekate) payloads bundled as default.

## Usage
* Launch application.
* Find a cable to connect your device to the Nintendo Switch. For proper work, this should be a cable that is designed for data transmission, not just for charging. It is advisable to use an **A-to-C** cable and an **USB OTG** adapter.
* In the **"Payloads"** category, click the **"+"** button to select preloaded payload from your device's storage. Or simply transfer your payload to the Rekado folder in the device's memory. Or you can use the bundled payloads (**SX Loader/ReiNX/Hekate**).
* Enter your Nintendo Switch into **RCM** mode in any convenient way. Your Nintendo Switch will power on by itself when plugged in, be sure to hold **VOLUME +**.
* Connect the device to the Nintendo Switch and allow permission for the **USB** access if necessary. Wait unit payloads chooser dialog will be opened and select which one you want to load.
* Wait for payload to finish loading on your console.

## Download
You can get APK (installation file) from [Releases](https://github.com/MenosGrante/Rekado/releases) form in this repository.

## FAQ
**Does application require Root?**

Application doesn't require Root on your device.

**Can it brick my device/console?**

This should not happen when using the "correct" payloads. But I am not responsible for possible problems.

**I want to help in Rekado's localization to my language, what should I do?**

Rekado's localization files placed in few places:
* \app\src\main\res\values\strings.xml
* \app\src\main\res\values\arrays.xml
* \konae\src\main\res\values\strings.xml

You should translate it. Files marked with **translatable="false"** should not be included in your localization. After finishing create **Pull request** and your localization will be added in Rekado.

## Credits
* [DavidBuchanan314](https://github.com/DavidBuchanan314) for creating NXLoader.
* [ealfonso93](https://github.com/ealfonso93) for contributing in this project.
* [unbranched](https://github.com/unbranched) for Italian localization.
* [wendyliga](https://github.com/wendyliga) for Indonesian localization.
* [javito1081](https://github.com/javito1081) for Spanish localization.
* [TheSergioEduP](https://github.com/TheSergioEduP) for Portuguese localization.
* [tiliarou](https://github.com/tiliarou) for French localization.

## Donate
I have created and supporting this application in my free time, so if you want to support me, follow my <a href="https://www.buymeacoffee.com/pavelrekun" target="_blank">"Buy me coffee"</a> link :)
