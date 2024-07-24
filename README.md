## 💘 팀원 
| **공채연** | **이현지** | **이은재** | 
| :------: |  :------: | :------: | 
| [<img src="https://avatars.githubusercontent.com/u/106502312?v=4" height=150 width=150> <br/> @chaeyeonKong](https://github.com/chaeyeonKong) | [<img src="https://avatars.githubusercontent.com/u/110108243?v=4" height=150 width=150> <br/> @Amepistheo](https://github.com/Amepistheo) | [<img src="https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2F20130307_24%2Fcocowablog_13626569090078ScMB_PNG%2F%25B6%25D7%25C0%25CC.png&type=sc960_832" height=150 width=150> <br/> @lej8924](https://github.com/lej8924) | 
| `아티클, 퀴즈 생성`  | `메인화면, 티클문답, CICD 환경 구축` | `로그인, 마이페이지, 크롤링` |

## 🔧 Tech Stack
- **Language**: Java 17
- **Library & Framework** : Spring Boot 3.3.1
- **Database** : AWS RDS (MySQL), AWS S3
- **ORM** : JPA
- **Deploy**: AWS EC2, Docker, Gihub Action, Nginx
- **Dependency**: Lombok, Swagger, Spring Security, JWT, SMTP, Redis

### 🌱 ERD
![image](https://github.com/user-attachments/assets/400e528f-a6d6-4a91-b54a-aaa8dad1e419)

### 🛠️ Infra Structure
![image](https://github.com/user-attachments/assets/e9bedb10-9a07-4057-ab03-99d2e63933af)

## 🗂️ Project Structure

```markdown
src
├── main
│   ├── global
│   ├── home
│   ├── memo
│   ├── mypage
│   ├── opinion
│   ├── post
│   ├── scrapped
│   └── user
│       ├── controller
│       ├── domain
│       ├── dto
│       |    ├── request
│       |    └── response
│       ├── exception
│       ├── repository
│       └── service
└── resources
    └── application.yml
```
