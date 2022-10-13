object Dependencies {
    /* kotlin */
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${ConfigData.kotlin_version}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    /* androidX */
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val testExt = "androidx.test.ext:junit:${Versions.ext}"
    const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val recyclerviewSelection =
        "androidx.recyclerview:recyclerview-selection:${Versions.recyclerviewSelection}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val lifecycleViewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewModelKtx}"
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"

    /* android.arch */
    const val navigationFragmentKtx =
        "android.arch.navigation:navigation-fragment-ktx:${Versions.navigationKtx}"
    const val navigationUiKtx =
        "android.arch.navigation:navigation-ui-ktx:${Versions.navigationKtx}"

    /* retrofit2 : https://github.com/square/retrofit */
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"

    // retrofit2Mock: Retrofit 라이브러리 응답으로 가짜 객체를 만들기 위함
    const val retrofit2Mock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit2}"

    // retrofit2ConverterGson : Retrofit에서 Gson을 사용하기 위한 라이브러리
    const val retrofit2ConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"

    const val retrofit2AdapterRxJava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit2AdapterRxjava2}"

    /* okttp3 */
    const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
    const val okhttp3LoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okttp3LoggingInterceptor}"

    /* gson : https://github.com/google/gson */
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    /* glide */
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"

    /* rxjava2 */
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava2}"
    const val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}"

    /* Lottie */
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"

    /* test */
    const val junit = "junit:junit:${Versions.junit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    /* legacy */
    const val supportV4 = "androidx.legacy:legacy-support-v4:${Versions.legacySupportV4}"

    /* flexbox : https://github.com/google/flexbox-layout*/
    const val flexbox = "com.google.android:flexbox:${Versions.flexbox}"

    /* PageIndicator : https://github.com/chahine/pageindicator */
    const val pageIndicator = "com.github.chahine:pageindicator:${Versions.pageIndicator}"

    /* CircleIndicator : https://github.com/ongakuer/CircleIndicator */
    const val circleIndicator = "me.relex:circleindicator:${Versions.circleIndicator}"

    /**
        TODO : Change to other library or make custom library because this library was no more update
        RatingBar : https://github.com/hedge-hog/RatingBar
    */
    const val ratingBar = "com.hedgehog.ratingbar:app:${Versions.ratingbar}"

    /* CircleImageView : https://github.com/hdodenhof/CircleImageView */
    const val circleImageView = "de.hdodenhof:circleimageview:${Versions.circleImageView}"

    /* Chart : https://github.com/PhilJay/MPAndroidChart */
    const val chart = "com.github.PhilJay:MPAndroidChart:${Versions.chart}"

    /* StickyScrollView : https://github.com/amarjain07/StickyScrollView */
    const val stickyScrollView =
        "com.github.amarjain07:StickyScrollView:${Versions.stickyScrollView}"

    /* Firebase SDK */
    const val gms = "com.google.gms:google-services:${Versions.gms}"
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebase}"
    const val firebaseKtx = "com.google.firebase:firebase-analytics-ktx"
}