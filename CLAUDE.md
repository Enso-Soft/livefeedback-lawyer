# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 프로젝트 개요

Lafi (Livefeedback Lawyer)는 변호사와 의뢰인을 연결하는 법률 서비스 관리 Android 애플리케이션입니다. AI 기반 법률 상담, 문서 공유, 일정 관리 기능을 제공하는 것을 목표로 합니다.

## 빌드 커맨드

### 기본 빌드 작업

```bash
# 전체 프로젝트 빌드
./gradlew build

# Debug APK 빌드 (demo 버전)
./gradlew assembleDemoDebug

# Release APK 빌드 (운영 버전)
./gradlew assembleProdRelease

# 빌드 산출물 정리
./gradlew clean
```

### 테스트

```bash
# 모든 유닛 테스트 실행
./gradlew test

# 연결된 기기에서 instrumented 테스트 실행
./gradlew connectedAndroidTest

# 특정 flavor 테스트 실행
./gradlew testDemoDebugUnitTest
```

### 코드 품질

```bash
# 모든 검사 실행 (lint, 테스트 등)
./gradlew check
```

## 아키텍처

### 멀티 모듈 Clean Architecture

프로젝트는 **Clean Architecture**를 따르며 계층을 엄격하게 분리합니다:

```
┌─────────────────────────────────────────────────┐
│  Presentation Layer (feature:*)                 │
│  - Activities, ViewModels, BottomSheets         │
│  - StateFlow/SharedFlow를 사용한 MVVM 패턴      │
└────────────────┬────────────────────────────────┘
                 │ ViewModel → Use Case
┌────────────────▼────────────────────────────────┐
│  Domain Layer (core:domain)                     │
│  - Use Cases (비즈니스 로직)                     │
│  - Repository 인터페이스                        │
│  - Domain Models (DataResult 등)                │
│  - 순수 Kotlin (Android 의존성 없음)            │
└────────────────┬────────────────────────────────┘
                 │ Use Case → Repository
┌────────────────▼────────────────────────────────┐
│  Data Layer (core:data, core:network)           │
│  - Repository 구현체                             │
│  - Data Sources (Remote/Local)                  │
│  - API Models & Mappers                         │
│  - ApiResult → DataResult 변환                  │
└─────────────────────────────────────────────────┘
```

### 주요 아키텍처 원칙

1. **의존성 방향**: 항상 안쪽을 향함 (Presentation → Domain ← Data)
2. **도메인 독립성**: `core:domain`은 순수 Kotlin이며 Android/프레임워크 의존성 없음
3. **Feature 격리**: Feature 모듈(`feature:*`)은 서로 의존하지 않음
4. **Use Case 패턴**: 각 비즈니스 작업은 별도의 Use Case 클래스로 구현
5. **Repository 패턴**: Domain에서 인터페이스 정의, Data 계층에서 구현
6. **Result 래퍼**: `DataResult<T>` sealed class로 모든 repository 응답을 래핑

### 모듈 구조

**Core 모듈:**
- `core:model` - 모든 계층에서 공유하는 순수 JVM 데이터 모델
- `core:domain` - 비즈니스 로직 (Use Cases, Repository 인터페이스, domain models)
- `core:data` - Repository 구현체, data source 인터페이스
- `core:network` - Retrofit API, network data sources, HTTP 설정
- `core:design-system` - 재사용 가능한 UI 컴포넌트 (TopBarLayout, TextInputLayout 등)
- `core:util` - 유틸리티 함수 (유효성 검사, 전화번호 포맷팅, 키보드 옵저버)
- `core:database` - Room 데이터베이스 (현재 미사용이지만 설정됨)

**Feature 모듈:**
- `feature:login` - 소셜 로그인 (카카오) 및 LoginViewModel
- `feature:signup` - 전화번호 인증 플로우를 포함한 회원가입
- `feature:home` - 메인 화면 (초기 단계)

**App 모듈:**
- `app` - 애플리케이션 진입점, 모든 feature 통합

### 빌드 설정

프로젝트는 `build-logic/convention/`에 위치한 **Convention Plugins** (Now in Android에서 영감받음)를 사용합니다:

- `AndroidApplicationConventionPlugin` - `app` 모듈에 적용
- `AndroidFeatureConventionPlugin` - `feature:*` 모듈에 적용
- `AndroidLibraryConventionPlugin` - `core:*` Android 라이브러리에 적용
- `JvmLibraryConventionPlugin` - 순수 Kotlin 모듈에 적용
- `HiltConventionPlugin` - Hilt DI 설정
- `AndroidApplicationFlavorsConventionPlugin` - Product flavors (demo/prod)

### Product Flavors

`contentType` dimension을 가진 두 가지 flavor:
- **demo** - 데모 모드, suffix `.demo` (예: `com.lafi.lawyer.demo`)
- **prod** - 운영 모드 (예: `com.lafi.lawyer`)

## 데이터 플로우 패턴

### Repository 계층 결과 처리

모든 repository 메서드는 `DataResult<T>`를 반환합니다:

```kotlin
sealed class DataResult<out T> {
    data class Success<T>(val data: T): DataResult<T>()
    data class Error(val error: ErrorData): DataResult<Nothing>()
}
```

**중요**: Data 계층에서는 `.map()` 확장 함수를 사용하여 `ApiResult`를 `DataResult`로 변환합니다. 새로운 repository 메서드를 구현할 때는 항상 이 패턴을 따라야 합니다 (`AuthRepositoryImpl.kt` 참고).

### ViewModel 이벤트 처리

ViewModel은 일회성 이벤트를 위해 SharedFlow와 함께 **Intent 패턴**을 사용합니다:

```kotlin
// ViewModel에서
private val _event = MutableSharedFlow<SignupIntent>()
val event = _event.asSharedFlow()

// 이벤트 발행
_event.emit(SignupIntent.NavigateToLogin)

// Activity에서 이벤트 수집
viewModel.event.collectLatest { intent ->
    when (intent) {
        is SignupIntent.NavigateToLogin -> { /* 처리 */ }
    }
}
```

### Mapper 패턴

Data 계층은 API 모델과 동일한 파일에 정의된 `.toDomain()` 확장 함수를 사용하여 API 모델을 domain 모델로 변환합니다.

## 현재 구현 상태

### 완료된 기능
- 카카오 소셜 로그인 통합
- 유효성 검사를 포함한 회원가입 UI
- SMS 인증 Use Cases (`SmsVerifyRequestUseCase`, `SmsVerifyUseCase`)
- 전화번호 포맷팅 (`PhoneNumberTextWatcher`)
- 커스텀 디자인 시스템 컴포넌트 (애니메이션, bottom sheets, input layouts)

### 진행 중
- `SocialSignupUseCase`의 25번째 줄에 TODO 존재
- SMS 인증 플로우 UI 통합

### 알려진 이슈
- KSP 버전 경고: `ksp-2.1.0-1.0.29 is too old for kotlin-2.1.10`
- demo/prod flavors에 대한 `buildConfigFields` 경고 (buildConfig 비활성화됨)

## 개발 노트

### Hilt를 사용한 의존성 주입

- 모든 Use Cases, Repositories, ViewModels에서 Hilt 사용
- Data sources와 API 인터페이스는 모듈을 통해 제공됨
- Feature 모듈은 Use Cases를 위해 `core:domain`에 의존 (수동 생성이 아닌 주입)

### API 설정

- Base URL과 secrets는 `secrets-gradle-plugin`으로 관리
- 카카오 SDK repository: `https://devrepo.kakao.com/nexus/content/groups/public/`
- Network 계층은 로깅 인터셉터와 함께 Retrofit + OkHttp 사용
- JSON을 위해 Kotlinx Serialization 사용 (Gson/Moshi 아님)

### UI 패턴

- 모든 UI에 **ViewBinding** 사용 (Compose 아님)
- 커스텀 애니메이션: `SlideIn` (우→좌), `ScaleRipple`
- Activity 전환은 `res/anim/`에 정의
- Bottom sheets는 `BottomSheetDialogFragment`를 확장
- `ValidationUtils.kt`에서 입력 유효성 검사 (이메일, 전화번호)

### 테스트 구조

- Unit tests: 각 모듈의 `test/` 디렉토리
- Instrumented tests: `androidTest/` 디렉토리
- Managed test devices는 `GradleManagedDevices.kt`에서 설정

## 새 기능 추가하기

### 새 Feature 모듈 추가

1. `lafi.android.feature` convention plugin으로 모듈 생성
2. 필요한 `core` 모듈에 의존성 추가 (일반적으로 `core:domain`, `core:design-system`)
3. MVVM 패턴 따르기: Activity → ViewModel → Use Case
4. 의존성 주입을 위해 Hilt 사용
5. `settings.gradle.kts`와 `app/build.gradle.kts`에 모듈 포함

### 새 Use Case 추가

1. `core:domain/usecase/`에 정의
2. `core:domain/repository/`에 repository 인터페이스 메서드 생성
3. `core:data/repository/`에 repository 메서드 구현
4. `core:network/retrofit/lafi/api/`에 API 엔드포인트 추가
5. `.toDomain()` mapper와 함께 request/response 모델 생성
6. ViewModel에 Use Case 주입

### 새 API 엔드포인트 추가

1. `AuthApi.kt`에 정의 (또는 새 API 인터페이스 생성)
2. `core:network/model/`에 request/response 모델 생성
3. `AuthDataSourceImpl.kt`에 data source 메서드 구현
4. `AuthRepositoryImpl.kt`에 `.map { it.toDomain() }`와 함께 repository 메서드 추가
5. `core:domain`의 repository 인터페이스를 통해 노출
