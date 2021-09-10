object Libraries {

    object AndroidX {
        val Core by lazy { "androidx.core:core-ktx:${Versions.AndroidX.Core}" }
        val RecyclerView by lazy { "androidx.recyclerview:recyclerview:${Versions.AndroidX.RecyclerView}" }
        val Browser by lazy { "androidx.browser:browser:${Versions.AndroidX.Browser}" }
        val Preferences by lazy { "androidx.preference:preference-ktx:${Versions.AndroidX.Preferences}" }
        val ConstraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.ConstraintLayout}" }
        val LifecycleViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.Lifecycle}" }
        val LifecycleLiveData by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.Lifecycle}" }
        val LifecycleCommon by lazy { "androidx.lifecycle:lifecycle-common-java8:${Versions.AndroidX.Lifecycle}" }
        val NavigationFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.Navigation}" }
        val NavigationUI by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.Navigation}" }
    }

    object Design {
        val MaterialComponents by lazy { "com.google.android.material:material:${Versions.Design.MaterialComponents}" }
        val FlexboxLayout by lazy { "com.google.android.flexbox:flexbox:${Versions.Design.FlexboxLayout}" }
        val Insetter by lazy { "dev.chrisbanes.insetter:insetter:${Versions.Design.Insetter}" }
    }

    object Tools {
        val EventBus by lazy { "org.greenrobot:eventbus:${Versions.Tools.EventBus}" }
        val Gson by lazy { "com.google.code.gson:gson:${Versions.Tools.Gson}" }
        val Retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.Tools.Retrofit}" }
        val Zxing by lazy { "com.journeyapps:zxing-android-embedded:${Versions.Tools.Zxing}" }
        val AppUpdater by lazy { "com.github.javiersantos:AppUpdater:${Versions.Tools.AppUpdater}" }
    }

    object Kotlin {
        val Kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Kotlin.Kotlin}" }
        val Coroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.Coroutines}" }
    }

}