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
        val SwipeRefreshLayout by lazy { "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.AndroidX.SwipeRefreshLayout}" }
        val Hilt by lazy { "com.google.dagger:hilt-android:${Versions.AndroidX.Hilt}" }
    }

    object Design {
        val MaterialComponents by lazy { "com.google.android.material:material:${Versions.Design.MaterialComponents}" }
        val FlexboxLayout by lazy { "com.google.android.flexbox:flexbox:${Versions.Design.FlexboxLayout}" }
        val Insetter by lazy { "dev.chrisbanes.insetter:insetter:${Versions.Design.Insetter}" }
    }

    object Tools {
        val Moshi by lazy { "com.squareup.moshi:moshi-kotlin:${Versions.Tools.Moshi}" }
        val Retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.Tools.Retrofit}" }
        val RetrofitMoshi by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.Tools.Retrofit}" }
        val Zxing by lazy { "com.journeyapps:zxing-android-embedded:${Versions.Tools.Zxing}" }
        val OkHttpLogging by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.Tools.OkHttpLogging}" }
    }

    object Kotlin {
        val Kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Kotlin.Kotlin}" }
        val Coroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.Coroutines}" }
    }

    object Compilers {
        val Hilt by lazy { "com.google.dagger:hilt-android-compiler:${Versions.AndroidX.Hilt}" }
        val Moshi by lazy { "com.squareup.moshi:moshi-kotlin-codegen:${Versions.Tools.Moshi}" }
    }

    object Other {
        val Desugaring by lazy { "com.android.tools:desugar_jdk_libs:${Versions.Other.Desugaring}" }
    }

}