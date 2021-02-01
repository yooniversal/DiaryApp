# DiaryApp
> 자세한 내용은 [[Android Studio] DiaryApp 구조](https://yooniversal.github.io/blog/post173/)를 참고해주세요.

Kotlin으로 작성되었으며, 구현한 부분은 크게 2가지 입니다.<br>

- 회원가입/로그인/로그아웃
- 메모 기능(추가/수정/삭제)
<br>

폰트는 [*네이버 마루부리*](https://hangeul.naver.com/)를 사용했습니다.

## Screenshots
<br>
먼저 스플래시 화면, 로그인 그리고 회원가입 화면은 다음과 같습니다.<br>
<img src="https://drive.google.com/uc?id=1Fa9z89YBh9iRS8co60kKnSXIgWZPRtkU" width="80%" height="80%" title="splash_login_signup.png" alt="?"/>

서버는 Django REST Framework로 만들었으며 로그인, 회원가입 모두 POST로 동작합니다.<br>
(DELETE도 [Insomnia API](https://insomnia.rest/)에서 정상적으로 동작합니다)<br>
<br>
다음은 메인화면, 작성 기본 화면, 작성 후 화면입니다.<br>
우측 하단에 있는 추가 버튼을 누르면 **작성 화면으로 진입**하도록 처리했습니다.<br>
DB는 [Room](https://developer.android.com/jetpack/androidx/releases/room?hl=ko)을 적용했습니다.<br>
<br>
<img src="https://drive.google.com/uc?id=1HfcQv5Eb0L0vAylA6kOLeld89skH6Qeq" width="80%" height="80%" title="memo_screens.png" alt="?"/>

메인화면에 메모 리스트중 하나를 누르면 **수정 화면으로 진입**하도록 처리했고,<br>
동시에 Actionbar에 있는 메뉴에서 **삭제 기능이 활성화**되도록 했습니다.<br>
<br>
<img src="https://drive.google.com/uc?id=1ylBTDzXvP1s7DHsT3huX_qiNJyqeeRVU" width="50%" height="50%" title="memo_delete_screens.png" alt="?"/><br>

## 예외 처리
차례대로 날짜가 형식에 맞지 않을 때, 제목이 비거나 22자가 넘을 때, 내용이 비었을 때입니다.<br>
한글 기준으로 2줄이 넘어가지 않게끔 처리하기 위해 제목 상한을 22자로 설정했습니다.<br>
`AddNoteFragment.kt`에서 확인할 수 있습니다. (`onActivityCreated()`, `checkDate()`)<br>
<br>
<img src="https://drive.google.com/uc?id=1EEuBN-gjsDmGQi34TDBrk3vKB9qfVhJT" width="90%" height="90%" title="memo_edit_screens.png" alt="?"/><br>
<br>

## Debugging
- lateinit property binding has not been initialized (+ 추가 버튼(in HomeFragment) 작동X)<br>
`Activity`, `Fragment`, `Adapter`에서 ViewBinding은 적용법이 서로 다름.<br>
<br>

`Activity`에는 클래스 초반부에 `private lateinit var binding: MainAcitvityBinding`,<br>
`onCreate()`에 `binding = ActivityMainBinding.inflate(layoutInflater), setContentView(binding.root)`을,<br>
`Fragment`의 `onCreateView()`에는 `binding = FragmentHomeBinding.inflate(inflater), return binding.root`을 추가.<br>
`Adapter`에서는 [[stackoverflow] How to do latest jetpack “View binding” in adapter, bind the views?](https://stackoverflow.com/questions/60491966/how-to-do-latest-jetpack-view-binding-in-adapter-bind-the-views/65779869#65779869) 참고.<br>
<br>

- ViewBinding 문제는 해결했지만 추가 버튼 여전히 작동X<br>
 `holder.view.setOnClickListener`를 `holder.binding.root.setOnClickListener`로 바꾸니 해결.<br>
 <br>

- 메모 목록을 한 줄에 하나씩 표시가 안됨<br>
`binding.recyclerViewNotes.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)`을 주석 처리하니 해결.<br>
한 줄에 3개의 항목들을 표시하겠다는 말이므로 이 코드를 없애지 않으면 다른 설정을 바꿔도 구조 자체는 변경이 되질 않음.<br>
<br>

- DB 구조 변경 후 기존 데이터와의 충돌<br>
```
Looks like you've changed schema but forgot to update the version number.
You can simply fix this by increasing the version number.
```
 라는 에러가 떴는데, 업데이트하지 않고, 앱 재설치로 해결.<br>
<br>

- 레이아웃이 ActionBar 및 상단바와 겹치는 현상<br>
xml에서 Layout 설정란에 `android:fitsSystemWindows="true"`추가.<br>

- 폰트 추가 후 `Duplicate resources` 에러 발생<br>
확장자는 다르지만 이름이 같은 폰트 파일이 2개가 같은 디렉토리에 있었음.<br>
둘 중 하나 삭제해서 해결.
