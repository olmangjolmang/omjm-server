-- User mockup data
ALTER TABLE opinion MODIFY comment_count BIGINT NOT NULL DEFAULT 0;

INSERT INTO users (agree_terms, email, nick_name, password, category)
VALUES (TRUE, 'user1@example.com', 'UserOne', 'password1', 'BACKEND'),
       (TRUE, 'user2@example.com', 'UserTwo', 'password2', 'WEB_FRONT'),
       (TRUE, 'user3@example.com', 'UserThree', 'password3', 'NETWORK'),
       (TRUE, 'user4@example.com', 'UserFour', 'password4', 'APP'),
       (TRUE, 'user5@example.com', 'UserFive', 'password5', 'SECURITY'),
       (TRUE, 'user6@example.com', 'UserSix', 'password6', 'AI'),
       (TRUE, 'user7@example.com', 'UserSeven', 'password7', 'VISION'),
       (TRUE, 'user8@example.com', 'UserEight', 'password8', 'INFRA'),
       (TRUE, 'user9@example.com', 'UserNine', 'password9', 'ETC'),
       (TRUE, 'user10@example.com', 'UserTen', 'password10', 'BACKEND'),
       (TRUE, 'user11@example.com', 'UserEleven', 'password11', 'WEB_FRONT'),
       (TRUE, 'user12@example.com', 'UserTwelve', 'password12', 'NETWORK'),
       (TRUE, 'user13@example.com', 'UserThirteen', 'password13', 'APP'),
       (TRUE, 'user14@example.com', 'UserFourteen', 'password14', 'SECURITY'),
       (TRUE, 'user15@example.com', 'UserFifteen', 'password15', 'AI'),
       (TRUE, 'user16@example.com', 'UserSixteen', 'password16', 'VISION'),
       (TRUE, 'user17@example.com', 'UserSeventeen', 'password17', 'INFRA'),
       (TRUE, 'user18@example.com', 'UserEighteen', 'password18', 'ETC'),
       (TRUE, 'user19@example.com', 'UserNineteen', 'password19', 'BACKEND'),
       (TRUE, 'user20@example.com', 'UserTwenty', 'password20', 'WEB_FRONT'),
       (TRUE, 'user21@example.com', 'UserTwentyOne', 'password21', 'NETWORK'),
       (TRUE, 'user22@example.com', 'UserTwentyTwo', 'password22', 'APP'),
       (TRUE, 'user23@example.com', 'UserTwentyThree', 'password23', 'SECURITY'),
       (TRUE, 'user24@example.com', 'UserTwentyFour', 'password24', 'AI'),
       (TRUE, 'user25@example.com', 'UserTwentyFive', 'password25', 'VISION'),
       (TRUE, 'user26@example.com', 'UserTwentySix', 'password26', 'INFRA'),
       (TRUE, 'user27@example.com', 'UserTwentySeven', 'password27', 'ETC'),
       (TRUE, 'user28@example.com', 'UserTwentyEight', 'password28', 'BACKEND'),
       (TRUE, 'user29@example.com', 'UserTwentyNine', 'password29', 'WEB_FRONT'),
       (TRUE, 'user30@example.com', 'UserThirty', 'password30', 'NETWORK');

-- You can add more mock data as needed

-- ----------------------------------------------------

-- Post mockup data

INSERT INTO post (title, content, author, created_date, category, image_file_name, image_folder_name, image_url, user_id, scrap_count)
VALUES ('비전공자 백엔드 취업 성공! 준비기간 1년 6개월! 과정부터 결과까지 싹 다 공개!',
        '개발을 처음 접하게 된 건 2019년 군 상병때였다. 뜬금없이 우리 부대에서 4차 산업혁명 기술 관련 창업 교육 프로그램이 개설되었고, 나는 운이 좋게 APP 개발 분과로 참여하게 되었다. (강원열린군대 1기)',
        '전반숙', '2024-07-06 12:00:00', 'BACKEND', 'TOP9_1.png', 'home/TOP9', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/TOP9/TOP9_1.png', 1, 124),
       ('웹 개발 인턴을 마치며,,, (2024 상반기 회고)',
        '인생에서 이때의 인턴의 경험은 개발자로서 현업에 나가기 전에 많은 도움을 받았습니다. 현업과 학부는 많이 달랐고, 그동안 개발을 해온 것이 크게 도움되어 작용되지는 않았다고 생각합니다. 그만큼 다시 시작하는 마음으로 많은 것을 배우고 기술적으로, 협업적으로 성장한 경험이었습니다.',
        'osohyun0224', '2024-07-15 12:00:00', 'WEB_FRONT', 'TOP9_2.png', 'home/TOP9', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/TOP9/TOP9_2.png', 1, 21),
       ('localhost 의 동작 원리',
        '내 PC에서 다른 서버가 아닌, 바로 나 자신을 가리키는 주소 입니다. 엄밀하게는 localhost 라는 글자 자체가 OS의 기본 로컬 라우팅 테이블에 부팅과 동시에 등재되는데요. localhost 는 호스트명일 뿐이고, 실제로는 IP주소를 사용합니다.',
        '김형섭 (Matthew)', '2024-06-05 12:00:00', 'NETWORK', 'TOP9_3.png', 'home/TOP9', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/TOP9/TOP9_3.png', 1, 21),
       ('[JavaScript] 기본 문법', '◉ 자바스크립트로 출력하기 document.write(’내용’); : HTML문서 내부에 출력. 이미 로딩된 문서를 모두 지우고 새로운 내용으로 덮어쓰기 때문에, 실행 시점과 위치에 유의',
        'MINJEE', '2023-12-09 12:00:00', 'WEB_FRONT', 'JS_1.png', 'home/JS', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/JS/JS_1.png', 1, 0),
       ('예제로 이해하는 자바스크립트 최신 문법 11가지',
        '자바스크립트 (Javascript)는 이제까지도 지속적으로 발전하며, 최신 트렌드를 반영한 혁신적인 문법들을 선보이고 있습니다. 이들 현대적인 문법들은 코드의 효율성을 높이며, 가독성을 향상시키는 동시에 코드의 실행 시간을 줄이는 성능상의 이점을 제공합니다.',
        'workee', '2024-06-02 14:08:00', 'WEB_FRONT', 'JS_2.png', 'home/JS', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/JS/JS_2.jpg', 1, 0),
       ('[JavaScript] 최신 문법 정리 (ES6 이후)', '자바스크립트의 혁명이라 할수 있는 ECMA Sript 2015(ES6) 이후 추가된 자바스크립트 최신 문법 중 자주 이용할 것 같은 기능들을 추려 정리해보는 시간이다.',
        'Eden', '2023-01-20 12:00:00', 'WEB_FRONT', 'JS_3.png', 'home/JS', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/JS/JS_3.png', 1, 0),
       ('좋은 개발 리더가 되기 위해 고민해본 것들',
        '이번 글에서는 지난 3년간 개인 기여자(Individual Contributor, IC)가 아닌 한 명의 리더로서 좋은 리더란 무엇인지, 또 좋은 리더가 되려면 어떤 역량이 필요한지에 스스로 고민해 봤던 내용에 관해 적어보려고 한다.',
        'Evan Moon', '2023-11-27 12:00:00', 'ETC', 'LEADER_1.png', 'home/LEADER', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/LEADER/LEADER_1.png', 1, 0),
       ('리더로 성장하고 싶은 개발자를 위한 3가지 기술',
        '매니지먼트는 프로젝트 관리, 팀 관리, 프로세스 관리로 구분할 수 있습니다. 첫 번째 프로젝트 관리는 출시 시기와 중점을 둬야 하는 일을 관리하는 기술입니다. 두 번째는 팀 관리, 즉 사람 관리입니다. 세 번째는 프로세스 관리입니다. 진행하는 과정을 관리하는 기술입니다.',
        '골든래빗', '2023-05-25 12:00:00', 'ETC', 'LEADER_2.png', 'home/LEADER', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/LEADER/LEADER_2.png', 1, 0),
       ('개발자 원칙',
        '개발 관련 신간을 구경하다가, 한번쯤 들어봤을 법한 회사에 종사하고 계시는 테크 리더 9분의 경험이 녹아있는 "개발자 원칙"이라는 책을 발견하게 되었다. 그리고 각 리더분들이 말하고자 하는 핵심 메시지들이 있고, 이것이 이 책의 목차이다. 나의 기준으로 핵심 메시지 중 가장 관심있는 메시지는 4가지가 있었다.',
        '강현석', '2023-01-15 12:00:00', 'ETC', 'LEADER_3.png', 'home/LEADER', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/LEADER/LEADER_3.png', 1, 0),
       ('한 권으로 끝내는 네트워크 기초',
        '컴퓨터 네트워크는 그 범위에 따라 랜(LAN, Local Area Network)와 왠(WAN, Wide Area Network)로 크게 나뉜다. 랜은 가정이나 사무실 등 하나의 거점 내부를 연결하는 네트워크이며 왠은 거점과 거점을 연결하는 네트워크이다. 즉, 거점 내부를 연결하는 것은 랜이고, 거점과 거점을 연결하는 것은 왠이다.',
        'City_Duck', '2023-03-13 12:00:00', 'NETWORK', 'NETWORK_1.png', 'home/NETWORK', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/NETWORK/NETWORK_1.png', 1, 0),
       ('[네트워크] 네트워크 기초 지식 정리',
        '네트워크 기술이란 서버와 클라이언트의 정보가 오고 가는 다리 역할을 하는 기술의 총칭을 의미한다. 네트워크라는 말은 연결되어 있다라는 뜻으로 컴퓨터 네트워크는 데이터를 케이블에 실어 나르는 것을 의미한다. (무선 LAN은 전파로 데이터를 실어 나른다.)',
        '지푸라기 개발자', '2019-09-03 04:11:00', 'NETWORK', 'NETWORK_2.png', 'home/NETWORK', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/NETWORK/NETWORK_2.png', 1, 0),
       ('네트워크 개념 정리',
        '인터넷 통신방식은 두가지 주체에 의해서 이루어집니다. 클라이언트와 서버입니다. 간단하게 이야기하자면 클라이언트에서 특정 정보를 서버에 요청하면 서버가 그에 해당하는 정보를 다시 클라이언트에게 전달해주는 개념입니다. 다만 인터넷이용자가 한 사람이 아니고 서버도 한개만 존재하는 것이 아닙니다.',
        '적어야 머리에 남는다!', '2021-05-02 12:00:00', 'NETWORK', 'NETWORK_3.png', 'home/NETWORK', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/NETWORK/NETWORK_3.png', 1, 0),
       ('쿠버네티스 리소스 배포와 관리를 위한 ksonnet',
        '쿠버네티스의 리소스 배포는 YAML 스크립트를 기반으로 한다. 하나의 마이크로 서비스를 배포하기 위해서는 최소한 Service, Deployment 두개 이상의 배포 스크립트를 작성해야 하고, 만약에 디스크를 사용한다면 Persistent Volume (aka PV)와 Persistent Volume Claim (PVC)등 추가로 여러 파일을 작성해서 배포해야 한다.',
        'Terry Cho', '2019-01-14 21:13:00', 'INFRA', 'CICD_1.png', 'home/CICD', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/CICD/CICD_1.png', 1, 0),
       ('[AWS] 운영 서버 관리 - 코드 배포',
        '현재 위치 배포 ( 무중단 배포 ) 새롭게 서버를 생성하거나 줄이지 않고 배포하는 것. 예) 절반은 잠시 로드 밸런서에서 제외하고 코드를 배포한 뒤 다시 로드 밸런서에 등록하고 나머지에 똑같은 방법을 진행하는 것',
        'ijnuemik', '2020-10-10 21:59:00', 'INFRA', 'CICD_2.png', 'home/CICD', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/CICD/CICD_2.png', 1, 0),
       ('ArgoCD란? 개념부터 설치 배포까지',
        'ArgoCD란? 기존의 소프트웨어를 배포하고 관리하는 방식은 문제점이 많았다. 인프라 환경을 수동적으로 관리하고, 소프트웨어와 인프라를 따로 관리하는 경우가 많았기에 이로 인해 인프라와 소프트웨어 간의 불일치가 발생하게 되었고, 배포 및 운영 과정에서 문제가 발생할 가능성이 높았다.',
        'wlsdn3004', '2023-05-12 12:00:00', 'INFRA', 'CICD_3.png', 'home/CICD', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/CICD/CICD_3.png', 1, 0),
       ('2024년 주목해야 할 5가지 AI 트렌드',
        '인공지능(AI) 환경은 전례 없는 속도로 진화하며 산업, 사회, 그리고 우리의 일상 생활을 바꾸어 놓고 있습니다. 2024년은 AI 여정에서 결정적인 순간이 될 것이며, 앞으로 지속적으로 영향을 줄 혁신적인 발전이 있을 것입니다. 지금부터 살펴볼 2024년에 주목해야 할 5가지 AI 트렌드는 우리가 마주하고 있는 AI의 향방을 정하는 데 중요한 역할을 할 것입니다.',
        '멜로디 자카리아스(Melody Zacharias)', '2024-02-05 12:00:00', 'AI', 'AI_1.png', 'home/AI', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/AI/AI_1.jpg', 1, 0),
       ('2024년 생성형 AI 트렌드 6가지 미리 보기',
        '불과 얼마 전까지, 영화 <백 투 더 퓨처>에서 자동차 “드로리안(DeLorean)”에 바나나 껍질과 맥주를 연료 삼아 넣는 건 그저 우습기만 한 설정이었습니다. 하지만 오늘날에는 전체 차량의 무려 10%가 전기로만 굴러가죠.1 1년 전까지만 해도, 자연어로 컴퓨터와 대화하는 건 SF에나 나올 일로 여겨졌지만, 이제는 다음 세대부터 개인용 AI 어시스턴트가 없는 삶은 상상할 수 없게 되었습니다.',
        '퀄컴 코리아', '2024-02-01 12:00:00', 'AI', 'AI_2.png', 'home/AI', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/AI/AI_2.png', 1, 0),
       ('2024년 주목해야 할 3가지 인공지능 핵심 트렌드?..."소형언어모델, 멀티모달 AI, 과학에서 AI"',
        '생성 인공지능(Generative AI)은 지난 한 해 동안 놀라운 발전을 이루며 인류의 일상 속에 자리잡았다. 특히 오픈ai 챗GPT, 구글 바드(Bard), 메타 라마(Llama), 마이크로소프트 코파일럿(Microsoft Copilot) 등과 같은 인기 도구를 통해 연구실에서부터 일상생활에 이르기까지 수많은 사람들이 AI를 활용하며 가능성을 확인했다.',
        '전미준', '2024-02-14 11:35:00', 'AI', 'AI_3.png', 'home/AI', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/AI/AI_3.jpg', 1, 0),
       ('제미나이 사용법 총정리 (Gemini Advanced 무료 사용법)',
        '구글의 대화형 AI 제미나이(Gemini)를 사용해 보셨나요? 이 글에서는 제미나이의 주요 기능과 사용법, 그리고 제미나이만이 가능한 활용 사례를 알려드리겠습니다.',
        '김지수', '2024-05-01 12:00:00', 'AI', 'GEMINI_1.png', 'home/GEMINI', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/GEMINI/GEMINI_1.png', 1, 0),
       ('제미나이(Gemini) 사용법 및 활용 사례: 유튜브 요약부터 블로그 글 작성까지',
        '제미나이는 블로그 글 작성을 자동화하는 데 도움이 되는 유용한 도구이지만, 아직 개발 초기 단계이며 완벽하지 않다는 점을 기억해야 합니다. 제미나이를 활용할 때는 반드시 직접 확인하고 수정 후 사용해야 하며, 저작권 침해에 주의해야 합니다.',
        '아야옹이다', '2024-05-25 12:00:00', 'AI', 'GEMINI_2.png', 'home/GEMINI', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/GEMINI/GEMINI_2.png', 1, 0),
       ('구글 생성형 AI 제미나이 Gemini 사용법, ChatGPT와 다른점',
        'ChatGPT에 대항하기 위한 론칭된 구글 바드 (Bard)에서 구글 제미나이 (Gemini)로 이름을 바꾼 구글 인공지능 AI 챗봇 서비스의 다양한 활용방법을 알아보겠습니다. 특히 제미나이는 텍스트 기반 ChatGPT와 달리 이미지, 동영상, 오디오까지 이해 가능한 구글의 생성형 AI로 구글의 모든 서비스에 통합되어 활용되는 강점이 있습니다.',
        'BigPine24', '2024-02-29 09:47:00', 'AI', 'GEMINI_3.png', 'home/GEMINI', 'https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/GEMINI/GEMINI_3.png', 1, 0),
       ('현대 애플리케이션에서의 인공지능 이해', '현대 애플리케이션에서 인공지능의 역할과 산업에 미치는 영향에 대한 통찰을 제공합니다.', '박지민', '2024-06-22 12:00:00', 'AI',
        'image6.png', 'folder6', 'https://example.com/image6.png', 1, 0),
       ('시각 컴퓨팅의 비전을 이끄는 접근: 응용 및 혁신', '시각 컴퓨팅 기술의 응용 및 혁신을 탐색하는 내용입니다.', '이승호', '2024-06-21 12:00:00', 'VISION',
        'image7.png', 'folder7', 'https://example.com/image7.png', 2, 0),
       ('인프라 관리의 비전을 제시하는 접근', '최신 인프라 관리 전략과 도구를 사용하여 시스템을 관리하는 방법에 대해 논의합니다.', '김민준', '2024-06-20 12:00:00', 'INFRA',
        'image8.png', 'folder8', 'https://example.com/image8.png', 3, 0),
       ('기타 다양한 주제에 대한 개발자들의 흥미진진한 이야기', '다양한 주제와 관련된 개발자들의 흥미로운 이야기를 담았습니다.', '최영희', '2024-06-19 12:00:00', 'ETC',
        'image9.png', 'folder9', 'https://example.com/image9.png', 1, 0),
       ('백엔드 성능 최적화를 위한 핵심 10가지 팁', '최신 기법과 도구를 활용한 백엔드 성능 최적화에 대한 심도 있는 안내서입니다.', '이재영', '2024-06-18 12:00:00',
        'BACKEND', 'image10.png', 'folder10', 'https://example.com/image10.png', 2, 0),
       ('반응형 웹 프론트엔드 개발의 전문가가 되기 위한 필수 요소', '반응형 웹 인터페이스 개발에 필요한 전문가가 되기 위한 필수 요소를 탐색하세요.', '한지우',
        '2024-06-17 12:00:00', 'WEB_FRONT', 'image11.png', 'folder11', 'https://example.com/image11.png', 1, 0),
       ('네트워크 보안의 핵심 전략과 기술 소개', '네트워크 보안에 대한 최신 전략과 기술 소개입니다.', '정영재', '2024-06-16 12:00:00', 'NETWORK', 'image12.png',
        'folder12', 'https://example.com/image12.png', 2, 0),
       ('애플리케이션 개발에서의 인공지능 활용', '현대 애플리케이션에서 인공지능의 활용 방법에 대해 알아보세요.', '이상민', '2024-06-15 12:00:00', 'APP',
        'image13.png', 'folder13', 'https://example.com/image13.png', 3, 0),
       ('보안 강화: 최신 보안 트렌드와 전략', '최신 보안 트렌드와 전략을 통해 보안 강화 방법을 탐색하세요.', '김현우', '2024-06-14 12:00:00', 'SECURITY',
        'image14.png', 'folder14', 'https://example.com/image14.png', 1, 0),
       ('AI 기술과 산업 응용의 진화', 'AI 기술이 산업에 미치는 영향과 응용 분야에 대해 탐구합니다.', '황지현', '2024-06-13 12:00:00', 'AI', 'image15.png',
        'folder15', 'https://example.com/image15.png', 2, 0),
       ('시각 컴퓨팅 기술의 최신 동향과 응용', '시각 컴퓨팅 기술의 최신 동향과 응용 분야에 대해 알아보세요.', '이도연', '2024-06-12 12:00:00', 'VISION',
        'image16.png', 'folder16', 'https://example.com/image16.png', 1, 0),
       ('인프라 관리의 최신 전문 지식과 기술', '최신 인프라 관리 전문 지식과 기술에 대해 자세히 알아보세요.', '김지수', '2024-06-11 12:00:00', 'INFRA',
        'image17.png', 'folder17', 'https://example.com/image17.png', 2, 0),
       ('다양한 주제에 대한 개발자들의 흥미로운 이야기', '다양한 주제와 관련된 개발자들의 흥미로운 이야기를 담고 있습니다.', '박동훈', '2024-06-10 12:00:00', 'ETC',
        'image18.png', 'folder18', 'https://example.com/image18.png', 3, 0),
       ('백엔드 개발의 중요한 포인트', '백엔드 개발에 있어 중요한 포인트를 다루는 내용입니다.', '정성민', '2024-06-09 12:00:00', 'BACKEND', 'image19.png',
        'folder19', 'https://example.com/image19.png', 1, 0),
       ('전문가들이 선택한 웹 프론트엔드 개발 전략', '웹 프론트엔드 개발 전략을 전문가들이 선택한 이유에 대해 알아보세요.', '김민지', '2024-06-08 12:00:00', 'WEB_FRONT',
        'image20.png', 'folder20', 'https://example.com/image20.png', 2, 0),
       ('네트워크 보안의 기술적 접근', '네트워크 보안에 대한 기술적 접근 방법을 다루는 내용입니다.', '이창호', '2024-06-07 12:00:00', 'NETWORK', 'image21.png',
        'folder21', 'https://example.com/image21.png', 1, 0),
       ('애플리케이션 개발에서의 인공지능 활용 방법', '애플리케이션 개발에서 인공지능의 활용 방법을 알아보세요.', '김미나', '2024-06-06 12:00:00', 'APP',
        'image22.png', 'folder22', 'https://example.com/image22.png', 2, 0),
       ('최신 보안 트렌드와 전략', '최신 보안 트렌드와 전략을 통해 보안을 강화하는 방법을 탐색하세요.', '박수진', '2024-06-05 12:00:00', 'SECURITY',
        'image23.png', 'folder23', 'https://example.com/image23.png', 3, 0),
       ('AI 기술과 산업 응용의 새로운 관점', 'AI 기술이 산업 응용 분야에 미치는 새로운 관점을 제시합니다.', '이승준', '2024-06-04 12:00:00', 'AI',
        'image24.png', 'folder24', 'https://example.com/image24.png', 1, 0),
       ('시각 컴퓨팅 기술의 최신 응용과 혁신', '시각 컴퓨팅 기술의 최신 응용과 혁신을 탐구합니다.', '김영호', '2024-06-03 12:00:00', 'VISION', 'image25.png',
        'folder25', 'https://example.com/image25.png', 2, 0),
       ('인프라 관리의 최신 전문 지식과 기술', '최신 인프라 관리 전문 지식과 기술에 대해 자세히 알아보세요.', '박진우', '2024-06-02 12:00:00', 'INFRA',
        'image26.png', 'folder26', 'https://example.com/image26.png', 1, 0),
       ('다양한 주제에 대한 개발자들의 흥미로운 이야기', '다양한 주제와 관련된 개발자들의 흥미로운 이야기를 담고 있습니다.', '임채은', '2024-06-01 12:00:00', 'ETC',
        'image27.png', 'folder27', 'https://example.com/image27.png', 2, 0),
       ('백엔드 개발에서의 최신 트렌드와 전망', '백엔드 개발에서의 최신 트렌드와 전망을 다루는 내용입니다.', '김태우', '2024-05-31 12:00:00', 'BACKEND',
        'image28.png', 'folder28', 'https://example.com/image28.png', 3, 0),
       ('웹 프론트엔드 개발 전문가들이 추천하는 전략', '웹 프론트엔드 개발 전문가들이 추천하는 전략을 탐색하세요.', '이재현', '2024-05-30 12:00:00', 'WEB_FRONT',
        'image29.png', 'folder29', 'https://example.com/image29.png', 1, 0),
       ('네트워크 보안의 최신 기술과 접근 방법', '네트워크 보안에 대한 최신 기술과 접근 방법을 알아보세요.', '김동욱', '2024-05-29 12:00:00', 'NETWORK',
        'image30.png', 'folder30', 'https://example.com/image30.png', 2, 0);

insert into scrapped (post_id,user_id)
values (1,1),
       (2,1),
       (3,1),
       (4,1),
       (1,2),
       (6,2);
