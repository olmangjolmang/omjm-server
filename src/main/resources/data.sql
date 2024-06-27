-- User mockup data

INSERT INTO users (email, nickname, password, category) VALUES
                                                            ('user1@example.com', 'UserOne', 'password1', 'BACKEND'),
                                                            ('user2@example.com', 'UserTwo', 'password2', 'WEB_FRONT'),
                                                            ('user3@example.com', 'UserThree', 'password3', 'NETWORK'),
                                                            ('user4@example.com', 'UserFour', 'password4', 'APP'),
                                                            ('user5@example.com', 'UserFive', 'password5', 'SECURITY'),
                                                            ('user6@example.com', 'UserSix', 'password6', 'AI'),
                                                            ('user7@example.com', 'UserSeven', 'password7', 'VISION'),
                                                            ('user8@example.com', 'UserEight', 'password8', 'INFRA'),
                                                            ('user9@example.com', 'UserNine', 'password9', 'ETC'),
                                                            ('user10@example.com', 'UserTen', 'password10', 'BACKEND'),
                                                            ('user11@example.com', 'UserEleven', 'password11', 'WEB_FRONT'),
                                                            ('user12@example.com', 'UserTwelve', 'password12', 'NETWORK'),
                                                            ('user13@example.com', 'UserThirteen', 'password13', 'APP'),
                                                            ('user14@example.com', 'UserFourteen', 'password14', 'SECURITY'),
                                                            ('user15@example.com', 'UserFifteen', 'password15', 'AI'),
                                                            ('user16@example.com', 'UserSixteen', 'password16', 'VISION'),
                                                            ('user17@example.com', 'UserSeventeen', 'password17', 'INFRA'),
                                                            ('user18@example.com', 'UserEighteen', 'password18', 'ETC'),
                                                            ('user19@example.com', 'UserNineteen', 'password19', 'BACKEND'),
                                                            ('user20@example.com', 'UserTwenty', 'password20', 'WEB_FRONT'),
                                                            ('user21@example.com', 'UserTwentyOne', 'password21', 'NETWORK'),
                                                            ('user22@example.com', 'UserTwentyTwo', 'password22', 'APP'),
                                                            ('user23@example.com', 'UserTwentyThree', 'password23', 'SECURITY'),
                                                            ('user24@example.com', 'UserTwentyFour', 'password24', 'AI'),
                                                            ('user25@example.com', 'UserTwentyFive', 'password25', 'VISION'),
                                                            ('user26@example.com', 'UserTwentySix', 'password26', 'INFRA'),
                                                            ('user27@example.com', 'UserTwentySeven', 'password27', 'ETC'),
                                                            ('user28@example.com', 'UserTwentyEight', 'password28', 'BACKEND'),
                                                            ('user29@example.com', 'UserTwentyNine', 'password29', 'WEB_FRONT'),
                                                            ('user30@example.com', 'UserThirty', 'password30', 'NETWORK');

-- You can add more mock data as needed

-- ----------------------------------------------------

-- Post mockup data


SELECT * FROM ticledb.post;INSERT INTO Post (title, content, author, create_date, post_category, image_file_name, image_folder_name, image_url, user_id)
                           VALUES
                               ('Sample Title 1', 'Sample content for post 1', 'Author 1', '2024-06-27 12:00:00', 'BACKEND', 'image1.png', 'folder1', 'https://example.com/image1.png', 1),
                               ('Sample Title 2', 'Sample content for post 2', 'Author 2', '2024-06-26 12:00:00', 'WEB_FRONT', 'image2.png', 'folder2', 'https://example.com/image2.png', 2),
                               ('Sample Title 3', 'Sample content for post 3', 'Author 3', '2024-06-25 12:00:00', 'NETWORK', 'image3.png', 'folder3', 'https://example.com/image3.png', 3),
                               ('Sample Title 4', 'Sample content for post 4', 'Author 4', '2024-06-24 12:00:00', 'APP', 'image4.png', 'folder4', 'https://example.com/image4.png', 1),
                               ('Sample Title 5', 'Sample content for post 5', 'Author 5', '2024-06-23 12:00:00', 'SECURITY', 'image5.png', 'folder5', 'https://example.com/image5.png', 2),
                               ('Sample Title 6', 'Sample content for post 6', 'Author 1', '2024-06-22 12:00:00', 'AI', 'image6.png', 'folder6', 'https://example.com/image6.png', 1),
                               ('Sample Title 7', 'Sample content for post 7', 'Author 2', '2024-06-21 12:00:00', 'VISION', 'image7.png', 'folder7', 'https://example.com/image7.png', 2),
                               ('Sample Title 8', 'Sample content for post 8', 'Author 3', '2024-06-20 12:00:00', 'INFRA', 'image8.png', 'folder8', 'https://example.com/image8.png', 3),
                               ('Sample Title 9', 'Sample content for post 9', 'Author 4', '2024-06-19 12:00:00', 'ETC', 'image9.png', 'folder9', 'https://example.com/image9.png', 1),
                               ('Sample Title 10', 'Sample content for post 10', 'Author 5', '2024-06-18 12:00:00', 'BACKEND', 'image10.png', 'folder10', 'https://example.com/image10.png', 2),
                               ('Sample Title 11', 'Sample content for post 11', 'Author 1', '2024-06-17 12:00:00', 'WEB_FRONT', 'image11.png', 'folder11', 'https://example.com/image11.png', 1),
                               ('Sample Title 12', 'Sample content for post 12', 'Author 2', '2024-06-16 12:00:00', 'NETWORK', 'image12.png', 'folder12', 'https://example.com/image12.png', 2),
                               ('Sample Title 13', 'Sample content for post 13', 'Author 3', '2024-06-15 12:00:00', 'APP', 'image13.png', 'folder13', 'https://example.com/image13.png', 3),
                               ('Sample Title 14', 'Sample content for post 14', 'Author 4', '2024-06-14 12:00:00', 'SECURITY', 'image14.png', 'folder14', 'https://example.com/image14.png', 1),
                               ('Sample Title 15', 'Sample content for post 15', 'Author 5', '2024-06-13 12:00:00', 'AI', 'image15.png', 'folder15', 'https://example.com/image15.png', 2),
                               ('Sample Title 16', 'Sample content for post 16', 'Author 1', '2024-06-12 12:00:00', 'VISION', 'image16.png', 'folder16', 'https://example.com/image16.png', 1),
                               ('Sample Title 17', 'Sample content for post 17', 'Author 2', '2024-06-11 12:00:00', 'INFRA', 'image17.png', 'folder17', 'https://example.com/image17.png', 2),
                               ('Sample Title 18', 'Sample content for post 18', 'Author 3', '2024-06-10 12:00:00', 'ETC', 'image18.png', 'folder18', 'https://example.com/image18.png', 3),
                               ('Sample Title 19', 'Sample content for post 19', 'Author 4', '2024-06-09 12:00:00', 'BACKEND', 'image19.png', 'folder19', 'https://example.com/image19.png', 1),
                               ('Sample Title 20', 'Sample content for post 20', 'Author 5', '2024-06-08 12:00:00', 'WEB_FRONT', 'image20.png', 'folder20', 'https://example.com/image20.png', 2),
                               ('Sample Title 21', 'Sample content for post 21', 'Author 1', '2024-06-07 12:00:00', 'NETWORK', 'image21.png', 'folder21', 'https://example.com/image21.png', 1),
                               ('Sample Title 22', 'Sample content for post 22', 'Author 2', '2024-06-06 12:00:00', 'APP', 'image22.png', 'folder22', 'https://example.com/image22.png', 2),
                               ('Sample Title 23', 'Sample content for post 23', 'Author 3', '2024-06-05 12:00:00', 'SECURITY', 'image23.png', 'folder23', 'https://example.com/image23.png', 3),
                               ('Sample Title 24', 'Sample content for post 24', 'Author 4', '2024-06-04 12:00:00', 'AI', 'image24.png', 'folder24', 'https://example.com/image24.png', 1),
                               ('Sample Title 25', 'Sample content for post 25', 'Author 5', '2024-06-03 12:00:00', 'VISION', 'image25.png', 'folder25', 'https://example.com/image25.png', 2),
                               ('Sample Title 26', 'Sample content for post 26', 'Author 1', '2024-06-02 12:00:00', 'INFRA', 'image26.png', 'folder26', 'https://example.com/image26.png', 1),
                               ('Sample Title 27', 'Sample content for post 27', 'Author 2', '2024-06-01 12:00:00', 'ETC', 'image27.png', 'folder27', 'https://example.com/image27.png', 2),
                               ('Sample Title 28', 'Sample content for post 28', 'Author 3', '2024-05-31 12:00:00', 'BACKEND', 'image28.png', 'folder28', 'https://example.com/image28.png', 3),
                               ('Sample Title 29', 'Sample content for post 29', 'Author 4', '2024-05-30 12:00:00', 'WEB_FRONT', 'image29.png', 'folder29', 'https://example.com/image29.png', 1),
                               ('Sample Title 30', 'Sample content for post 30', 'Author 5', '2024-05-29 12:00:00', 'NETWORK', 'image30.png', 'folder30', 'https://example.com/image30.png', 2);
