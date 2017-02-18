#Jarvis
  - 사실 아이언맨의 자비스를 만들고 싶어서 시작한 아이디어.
  - 추운 겨울날 전화를 받거나 음악들 듣기위해 주머니에서 손을 빼는것이 싫었다.
  - 무엇보다 핸드폰에 대고 '자비스?' 라고 말해주고 싶었다. '시리', '오케이구글' 보다...

 1. 소스코드보다 산출 문서의 학술 논문들이 메인.
 2. 내가 우수논문상을 받게되다니

# 실행화면

 ![자비스 실행 화면]()

# Jarvis의 특징
 
 1. 안드로이드, iOS에서 제공되는 접근성 플러그인 Talkback과 VoiceOver의 UX를 침범하지 않는다.
 2. 접근성 플러그인의 Screen Reader기능이 잘 작동할 수 있도록 텍스트 디스크립션을 달아주었다.
 3. OK Google 과 차별을 두자면 OK Google는 로컬에 존재하는 미디어파일 접근이 불가능

# 작동
 
 ![작동]()
 
 1. 핸드폰을 키고 5초 이내에 흔든다.
 2. '자비스?' 라고 말한다.
 3. 구글 음성 인식 API에서 문자열을 반환해준다.
 4. 반환된 문자열을 이용하여 SQLite에 저장시킨 앱의 이름과 매치한 앱을 실행시킨다.
 5. 같은 원리로 음악,이미지,영상과 같은 미디어파일은 이름으로 호출하는데 다운로드 받거나 카메라를 이용해 만든 미디어 파일은 날짜와 순서로 실행시킬 수 있다.
 6. 4번과 5번은 앱의 설치와 미디어파일의 발생시점에서 SQLite에 미디어파일의 이름을 저장하기 때문에 가능하다.
 
# 산출 문서
 - [융복합지식학술대회2016 우수상장]()
 - [Jarvis로 발표한 자료]()
 - [Jarvis로 쓴 논문](http://www.dbpia.co.kr/Journal/ArticleDetail/NODE06606108?TotalCount=1&Seq=1&q=%5B%EC%A0%91%EA%B7%BC%EC%84%B1%20%ED%94%8C%EB%9F%AC%EA%B7%B8%EC%9D%B8%EC%9D%84%20%EC%9D%B4%EC%9A%A9%ED%95%9C%20%EC%8B%9C%EA%B0%81%EC%9E%A5%EC%95%A0%EC%9D%B8%EC%9A%A9%20%EC%8A%A4%EB%A7%88%ED%8A%B8%ED%8F%B0%20%EB%A9%94%EB%89%B4%20%EC%8B%9C%EC%8A%A4%ED%85%9C%20%EC%84%A4%EA%B3%84%C2%A7coldb%C2%A72%C2%A751%C2%A73%5D&searchWord=%EC%A0%84%EC%B2%B4%3D%5E%24%EC%A0%91%EA%B7%BC%EC%84%B1%20%ED%94%8C%EB%9F%AC%EA%B7%B8%EC%9D%B8%EC%9D%84%20%EC%9D%B4%EC%9A%A9%ED%95%9C%20%EC%8B%9C%EA%B0%81%EC%9E%A5%EC%95%A0%EC%9D%B8%EC%9A%A9%20%EC%8A%A4%EB%A7%88%ED%8A%B8%ED%8F%B0%20%EB%A9%94%EB%89%B4%20%EC%8B%9C%EC%8A%A4%ED%85%9C%20%EC%84%A4%EA%B3%84%5E*&Multimedia=0&isIdentifyAuthor=0&Collection=0&SearchAll=%EC%A0%91%EA%B7%BC%EC%84%B1%20%ED%94%8C%EB%9F%AC%EA%B7%B8%EC%9D%B8%EC%9D%84%20%EC%9D%B4%EC%9A%A9%ED%95%9C%20%EC%8B%9C%EA%B0%81%EC%9E%A5%EC%95%A0%EC%9D%B8%EC%9A%A9%20%EC%8A%A4%EB%A7%88%ED%8A%B8%ED%8F%B0%20%EB%A9%94%EB%89%B4%20%EC%8B%9C%EC%8A%A4%ED%85%9C%20%EC%84%A4%EA%B3%84&isFullText=0&specificParam=0&SearchMethod=0&Sort=1&SortType=desc&Page=1&PageSize=20)
 - [Jarvis에서 파생된 웹UI/UX 발표자료]()
 - [Jarvis에서 파생된 웹UI/UX 논문](http://www.dbpia.co.kr/Journal/ArticleDetail/NODE06554544?TotalCount=2&Seq=1&q=%5B%EC%8A%A4%EB%A7%88%ED%8A%B8%ED%8F%B0%20%ED%99%98%EA%B2%BD%EC%97%90%EC%84%9C%20%EC%8B%9C%EA%B0%81%EC%9E%A5%EC%95%A0%EC%9D%B8%EC%9D%84%20%EC%9C%84%ED%95%9C%20%EC%9B%B9UX%20%EA%B5%AC%EC%A1%B0%20%EC%84%A4%EA%B3%84%C2%A7coldb%C2%A72%C2%A751%C2%A73%5D&searchWord=%EC%A0%84%EC%B2%B4%3D%5E%24%EC%8A%A4%EB%A7%88%ED%8A%B8%ED%8F%B0%20%ED%99%98%EA%B2%BD%EC%97%90%EC%84%9C%20%EC%8B%9C%EA%B0%81%EC%9E%A5%EC%95%A0%EC%9D%B8%EC%9D%84%20%EC%9C%84%ED%95%9C%20%EC%9B%B9UX%20%EA%B5%AC%EC%A1%B0%20%EC%84%A4%EA%B3%84%5E*&Multimedia=0&isIdentifyAuthor=0&Collection=0&SearchAll=%EC%8A%A4%EB%A7%88%ED%8A%B8%ED%8F%B0%20%ED%99%98%EA%B2%BD%EC%97%90%EC%84%9C%20%EC%8B%9C%EA%B0%81%EC%9E%A5%EC%95%A0%EC%9D%B8%EC%9D%84%20%EC%9C%84%ED%95%9C%20%EC%9B%B9UX%20%EA%B5%AC%EC%A1%B0%20%EC%84%A4%EA%B3%84&isFullText=0&specificParam=0&SearchMethod=0&Sort=1&SortType=desc&Page=1&PageSize=20)











