<center>
<img src="https://capsule-render.vercel.app/api?type=transparent&color=auto&height=300&section=header&text=A.fume&fontSize=90&animation=fadeIn" width="100%">
</center>

> SOPT - Term Project  
> Ver 1.01  2020.12.01 ~ 2021.10.22  
> [🏡홈페이지](https://afumeapp.wixsite.com/afume)

![afume_()](https://user-images.githubusercontent.com/51378843/157236142-99c9774a-d8b5-45b7-be71-00d10882a39a.png)
![afume_()_2](https://user-images.githubusercontent.com/51378843/157236148-6bfecf26-7919-458b-9168-a4f94c912afb.png)

### 향수 정보를 모으고 정리하여<br>누구든지 원하는 향수를 쉽게 발견하게 해주는 A.fume



### 🍋Main Function

| **Function**        | **Description**                                              |
| :------------------ | ------------------------------------------------------------ |
| **향수 추천**       | 흔한 향수 추천이 아닌, 당신의 취향에 맞는 향수들을 골라 추천해드립니다. |
| **향수 검색**       | 향 계열별 필터를 통해 내가 원하는 향의 향수들만 한 번에 모아볼 수 있습니다.** |
| **향수 정보**       | 향수의 노트 구성, 조향 스토리, 브랜드, 가격, 시향기 등 향수에 대한 모든 정보들을 한 곳에서 모아볼 수 있습니다. |
| **시향 위시리스트** | 혹시 나중에 맡아보고싶은 향수가 생겼나요? 위시리스트에 추가하여 보세요. 나중에 시향할 때 한 번에 볼 수 있습니다. |
| **시향 노트**       | 시향을 하셨나요? 그 순간 어떻게 느꼈는지 까먹기 전에 나만의 시향 노트에 적어보세요. 단순 메모뿐만 아니라 계절감, 성별, 지속력, 잔향감 등도 선택할 수 있습니다. |
| **향수 컬렉션**     | 그 동안 내가 썼던 향수들, 한 곳에 모아보세요. 나의 향수 취향을 한 눈에 파악할 수 있을거에요. |

 

### 🌿Information Architecture

![Artboard](https://user-images.githubusercontent.com/51378843/157236816-18f9cf5a-31c9-4370-9e1e-6c61175dfaa9.png)

### 🏕Develop Environment

|  Environment   | version |
| :------------: | :-----: |
|     Kotlin     | 1.4.20  |
| Android Studio |  4.1.1  |

### 🧩Project Architecture

- MVVM using AAC ViewModel
- DataBinding
- Coroutine

### 🗃Dependency

```java
dependencies {

    def navigation_ktx_version = '2.2.2'
    def material_version='1.2.1'

    //bottom navigation
    implementation "android.arch.navigation:navigation-fragment-ktx:$navigation_ktx_version"
    implementation "android.arch.navigation:navigation-ui-ktx:$navigation_ktx_version"
    //ViewModel - Android-KTX
    implementation "androidx.fragment:fragment-ktx:1.2.5"
    //생명주기를 공유하기 위한 라이브러리
    implementation "androidx.appcompat:appcompat:1.2.0"
    //LiveData
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"

    // View
    // Flexible layouts with flexLayout (recycler view integration)
    implementation 'com.google.android:flexbox:2.0.1'
    // CircleIndicator
    implementation 'me.relex:circleindicator:2.1.6'
    // Gilde-loadImage
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    // CircleImageView
    implementation 'de.hdodenhof:circleimageview:3.1.0'
    // Chart
    implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
    // Viewpager2
    implementation "androidx.viewpager2:viewpager2:1.0.0"
    // Lottie - gif 
    implementation 'com.airbnb.android:lottie:3.7.0'

    // Network 
    // <https://github.com/square/retrofit>
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    // Retrofit 라이브러리 응답으로 가짜 객체를 만들기 위함
    implementation 'com.squareup.retrofit2:retrofit-mock:2.9.0'
    // <https://github.com/google/gson>
    implementation 'com.google.code.gson:gson:2.8.6'
    // Retrofit에서 Gson을 사용하기 위한 라이브러리
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.7.1'
    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'
    // Okhttp
    implementation 'com.squareup.okhttp3:okhttp:3.14.9'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.8.1'
}
```
<br>

# 🍋Convention

### 🍰Naming Rule

- **drawables**

  - **shapable : border_[colorname]\*[shape(line or fill)]\*[radiusnumber].xml**

    ex) border_lightgray_line_32.xml

  - **selector : selector_[where]_[description].xml**

    ex) selector_note_season

  - **icon : icon_[name]**

- **view**

  - **elementtype_where_action**

    ex) btn_home_login

    - (Button) btn

    - (ImageView) img

    - (TextView) txt

    - (EditText) edt

    - (TabLayout) tab

    - (ViewPager) vp

    - (RecyclerView) rv

    - (RecyclerView Item) rv_item

      ex) rv_item_home_title

- **color**

  - **Primary or Point**

    ex) primary_blue / point_beige

  - **colorname_16진수 2자리**

    ex) light_gray_f9 / light_gray_f0

### 🌿Branching

- Git-flow

  ```
  master : 제품으로 출시될 수 있는 브랜치
  develop : 다음 출시 버전을 개발하는 브랜치
  feature : 기능을 개발하는 브랜치
  release : 이번 출시 버전을 준비하는 브랜치
  hotfix : 출시 버전에서 발생한 버그를 수정하는 브랜치
  ```

- 브랜치명

  - 브랜치접두어/[issue number]-[where]-[action]

    ex) feature/68-filter-network

### 🍡Issue Naming

- Labels
  - Android : 공통 작업
  - bug : 버그 수정
  - design : 뷰 작업
  - enhancement : 새로운 기능
  - network : 통신 작업
- category
  - layout : 뷰 작업
  - network : 통신 작업
  - feature : 기능 추가
  - refactor : 리팩토링
  - hotfix : 버그 수정

- Title

  - [category] title

    ex) [feature]  search

### 🥨Commit Message

- 깃 이슈 번호 추가

- 영어로 작성

- [TYPE] #이슈번호 : 내용
  ex) [feat] #1 : Add feature
  
</br>

# 🦉Developer
|                            정은이                            |                            천명희                            |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://user-images.githubusercontent.com/51378843/157241242-65b69f4a-e1f4-4775-b02c-8aa94b3326af.jpg" width="350" height="350"> | <img src="https://user-images.githubusercontent.com/39720852/157244840-a6ef9de8-0988-498b-9ac5-cc4cca1dd1f4.jpg" width="350" height="350"> |
|         [EuneeChung](https://github.com/EuneeChung)          |             [Haeeul](https://github.com/Haeeul)              |
| **검색** <br />• 필터로 검색    <br />◦ 계열/ 브랜드/ 키워드 <br />• 검색 결과 <br /><br />**마이** <br />• 마이 퍼퓸 선반 뷰<br /> • 위시리스트 <br /><br />**향수 상세 뷰** <br />• 향수 정보 <br /><br />**유저** <br />• 설문조사   <br />◦ 선호 취향 <br /><br />**스플래시**<br /> | **홈** <br />• 추천 향수 <br />• 새로운 향수 <br /><br />**마이** <br />• 시향 노트 작성    <br />◦ 평점/ 메모/ 지속감/ 잔향감/ 계절감 <br /><br />**향수 상세 뷰** <br />• 시향 노트 <br />• 향수 정보 - 그래프 수정 <br /><br />**유저** <br />• 회원가입 <br />• 로그인 <br />• 정보 수정 |
