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
INSERT INTO post (title, content, author, created_date, category, image_file_name, image_folder_name, image_url, scrap_count)
VALUES ('새로운 기법을 활용한 백엔드 성능 최적화 가이드', '최신 기법과 도구를 활용한 백엔드 성능 최적화에 대한 심도 있는 안내서입니다.', '홍길동', '2024-06-27 12:00:00', 'BACKEND', 'image1.png', 'folder1', 'https://example.com/image1.png', 0),
       ('반응형 웹 프론트엔드 개발을 위한 상위 10가지 팁', '사용자가 만족할 수 있는 반응형 웹 인터페이스를 구축하기 위한 필수 전략입니다.', '이영희', '2024-06-26 12:00:00', 'WEB_FRONT', 'image2.png', 'folder2', 'https://example.com/image2.png', 0),
       ('네트워크 보안 마스터하기: 최고의 실전 전략', '사이버 위협에 대비하여 네트워크 인프라를 안전하게 보호하는 고급 기술에 대해 알아보세요.', '박철수', '2024-06-25 12:00:00', 'NETWORK', 'image3.png', 'folder3', 'https://example.com/image3.png', 0),
       ('강력하고 확장 가능한 애플리케이션 개발: 핵심 원칙', '성장하는 요구를 충족시키는 확장 가능한 애플리케이션을 개발하는 데 필수적인 원칙을 배워보세요.', '김영수', '2024-06-24 12:00:00', 'APP', 'image4.png', 'folder4', 'https://example.com/image4.png', 0),
       ('웹 보안 강화: 최신 트렌드와 전략', '웹 보안을 강화하고 사이버 위협으로부터 보호하는 최신 트렌드와 전략을 탐색하세요.', '이지현', '2024-06-23 12:00:00', 'SECURITY', 'image5.png', 'folder5', 'https://example.com/image5.png', 0),
       ('현대 애플리케이션에서의 인공지능 이해', '현대 애플리케이션에서 인공지능의 역할과 산업에 미치는 영향에 대한 통찰을 제공합니다.', '박지민', '2024-06-22 12:00:00', 'AI', 'image6.png', 'folder6', 'https://example.com/image6.png', 0),
       ('시각 컴퓨팅의 비전을 이끄는 접근: 응용 및 혁신', '시각 컴퓨팅 기술의 응용 및 혁신을 탐색하는 내용입니다.', '이승호', '2024-06-21 12:00:00', 'VISION', 'image7.png', 'folder7', 'https://example.com/image7.png', 0),
       ('인프라 관리의 비전을 제시하는 접근', '최신 인프라 관리 전략과 도구를 사용하여 시스템을 관리하는 방법에 대해 논의합니다.', '김민준', '2024-06-20 12:00:00', 'INFRA', 'image8.png', 'folder8', 'https://example.com/image8.png', 0),
       ('기타 다양한 주제에 대한 개발자들의 흥미진진한 이야기', '다양한 주제와 관련된 개발자들의 흥미로운 이야기를 담았습니다.', '최영희', '2024-06-19 12:00:00', 'ETC', 'image9.png', 'folder9', 'https://example.com/image9.png', 0),
       ('백엔드 성능 최적화를 위한 핵심 10가지 팁', '최신 기법과 도구를 활용한 백엔드 성능 최적화에 대한 심도 있는 안내서입니다.', '이재영', '2024-06-18 12:00:00', 'BACKEND', 'image10.png', 'folder10', 'https://example.com/image10.png', 0),
       ('반응형 웹 프론트엔드 개발의 전문가가 되기 위한 필수 요소', '반응형 웹 인터페이스 개발에 필요한 전문가가 되기 위한 필수 요소를 탐색하세요.', '한지우', '2024-06-17 12:00:00', 'WEB_FRONT', 'image11.png', 'folder11', 'https://example.com/image11.png', 0),
       ('네트워크 보안의 핵심 전략과 기술 소개', '네트워크 보안에 대한 최신 전략과 기술 소개입니다.', '정영재', '2024-06-16 12:00:00', 'NETWORK', 'image12.png', 'folder12', 'https://example.com/image12.png', 0),
       ('애플리케이션 개발에서의 인공지능 활용', '현대 애플리케이션에서 인공지능의 활용 방법에 대해 알아보세요.', '이상민', '2024-06-15 12:00:00', 'APP', 'image13.png', 'folder13', 'https://example.com/image13.png', 0),
       ('보안 강화: 최신 보안 트렌드와 전략', '최신 보안 트렌드와 전략을 통해 보안 강화 방법을 탐색하세요.', '김현우', '2024-06-14 12:00:00', 'SECURITY', 'image14.png', 'folder14', 'https://example.com/image14.png', 0),
       ('AI 기술과 산업 응용의 진화', 'AI 기술이 산업에 미치는 영향과 응용 분야에 대해 탐구합니다.', '황지현', '2024-06-13 12:00:00', 'AI', 'image15.png', 'folder15', 'https://example.com/image15.png', 0),
       ('시각 컴퓨팅 기술의 최신 동향과 응용', '시각 컴퓨팅 기술의 최신 동향과 응용 분야에 대해 알아보세요.', '이도연', '2024-06-12 12:00:00', 'VISION', 'image16.png', 'folder16', 'https://example.com/image16.png', 0),
       ('인프라 관리의 최신 전문 지식과 기술', '최신 인프라 관리 전문 지식과 기술에 대해 자세히 알아보세요.', '김지수', '2024-06-11 12:00:00', 'INFRA', 'image17.png', 'folder17', 'https://example.com/image17.png', 0),
       ('다양한 주제에 대한 개발자들의 흥미로운 이야기', '다양한 주제와 관련된 개발자들의 흥미로운 이야기를 담고 있습니다.', '박동훈', '2024-06-10 12:00:00', 'ETC', 'image18.png', 'folder18', 'https://example.com/image18.png', 0),
       ('백엔드 개발의 중요한 포인트', '백엔드 개발에 있어 중요한 포인트를 다루는 내용입니다.', '정성민', '2024-06-09 12:00:00', 'BACKEND', 'image19.png', 'folder19', 'https://example.com/image19.png', 0),
       ('전문가들이 선택한 웹 프론트엔드 개발 전략', '웹 프론트엔드 개발 전략을 전문가들이 선택한 이유에 대해 알아보세요.', '김민지', '2024-06-08 12:00:00', 'WEB_FRONT', 'image20.png', 'folder20', 'https://example.com/image20.png', 0),
       ('네트워크 보안의 기술적 접근', '네트워크 보안에 대한 기술적 접근 방법을 다루는 내용입니다.', '이창호', '2024-06-07 12:00:00', 'NETWORK', 'image21.png', 'folder21', 'https://example.com/image21.png', 0),
       ('애플리케이션 개발에서의 인공지능 활용 방법', '애플리케이션 개발에서 인공지능의 활용 방법을 알아보세요.', '김미나', '2024-06-06 12:00:00', 'APP', 'image22.png', 'folder22', 'https://example.com/image22.png', 0),
       ('최신 보안 트렌드와 전략', '최신 보안 트렌드와 전략을 통해 보안을 강화하는 방법을 탐색하세요.', '박수진', '2024-06-05 12:00:00', 'SECURITY', 'image23.png', 'folder23', 'https://example.com/image23.png', 0),
       ('AI 기술과 산업 응용의 새로운 관점', 'AI 기술이 산업 응용 분야에 미치는 새로운 관점을 제시합니다.', '이승준', '2024-06-04 12:00:00', 'AI', 'image24.png', 'folder24', 'https://example.com/image24.png', 0),
       ('시각 컴퓨팅 기술의 최신 응용과 혁신', '시각 컴퓨팅 기술의 최신 응용과 혁신을 탐구합니다.', '김영호', '2024-06-03 12:00:00', 'VISION', 'image25.png', 'folder25', 'https://example.com/image25.png', 0),
       ('인프라 관리의 최신 전문 지식과 기술', '최신 인프라 관리 전문 지식과 기술에 대해 자세히 알아보세요.', '박진우', '2024-06-02 12:00:00', 'INFRA', 'image26.png', 'folder26', 'https://example.com/image26.png', 0),
       ('다양한 주제에 대한 개발자들의 흥미로운 이야기', '다양한 주제와 관련된 개발자들의 흥미로운 이야기를 담고 있습니다.', '임채은', '2024-06-01 12:00:00', 'ETC', 'image27.png', 'folder27', 'https://example.com/image27.png', 0),
       ('백엔드 개발에서의 최신 트렌드와 전망', '백엔드 개발에서의 최신 트렌드와 전망을 다루는 내용입니다.', '김태우', '2024-05-31 12:00:00', 'BACKEND', 'image28.png', 'folder28', 'https://example.com/image28.png', 0),
       ('웹 프론트엔드 개발 전문가들이 추천하는 전략', '웹 프론트엔드 개발 전문가들이 추천하는 전략을 탐색하세요.', '이재현', '2024-05-30 12:00:00', 'WEB_FRONT', 'image29.png', 'folder29', 'https://example.com/image29.png', 0),
       ('네트워크 보안의 최신 기술과 접근 방법', '네트워크 보안에 대한 최신 기술과 접근 방법을 알아보세요.', '김동욱', '2024-05-29 12:00:00', 'NETWORK', 'image30.png', 'folder30', 'https://example.com/image30.png', 0);

insert into scrapped (post_id,user_id)
values (1,1),
       (2,1),
       (3,1),
       (4,1),
       (1,2),
       (6,2);
