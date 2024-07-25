package com.ticle.server.post.repository.init;

import com.ticle.server.global.domain.S3Info;
import com.ticle.server.global.util.LocalDummyDataInit;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.user.domain.type.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@LocalDummyDataInit
public class PostInitializer implements ApplicationRunner {

    private final PostRepository postRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (postRepository.count() > 0) {
            log.info("[Post] 더미 데이터 존재");
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Post post1 = Post.builder()
                    .title("비전공자 백엔드 취업 성공! 준비기간 1년 6개월! 과정부터 결과까지 싹 다 공개!")
                    .content("개발을 처음 접하게 된 건 2019년 군 상병때였다. 뜬금없이 우리 부대에서 4차 산업혁명 기술 관련 창업 교육 프로그램이 개설되었고, 나는 운이 좋게 APP 개발 분과로 참여하게 되었다. (강원열린군대 1기)")
                    .author("전반숙")
                    .createdDate(dateFormat.parse("2024-07-06"))
                    .category(Category.BACKEND)
                    .image(S3Info.builder()
                            .imageFileName("TOP9_1.png")
                            .imageFolderName("home/TOP9")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/TOP9/TOP9_1.png")
                            .build())
                    .scrapCount(124)
                    .build();

            Post post2 = Post.builder()
                    .title("웹 개발 인턴을 마치며,,, (2024 상반기 회고)")
                    .content("인생에서 이때의 인턴의 경험은 개발자로서 현업에 나가기 전에 많은 도움을 받았습니다. 현업과 학부는 많이 달랐고, 그동안 개발을 해온 것이 크게 도움되어 작용되지는 않았다고 생각합니다. 그만큼 다시 시작하는 마음으로 많은 것을 배우고 기술적으로, 협업적으로 성장한 경험이었습니다.")
                    .author("osohyun0224")
                    .createdDate(dateFormat.parse("2024-07-15"))
                    .category(Category.WEB_FRONT)
                    .image(S3Info.builder()
                            .imageFileName("TOP9_2.png")
                            .imageFolderName("home/TOP9")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/TOP9/TOP9_2.png")
                            .build())
                    .scrapCount(21)
                    .build();

            Post post3 = Post.builder()
                    .title("localhost 의 동작 원리")
                    .content("내 PC에서 다른 서버가 아닌, 바로 나 자신을 가리키는 주소 입니다. 엄밀하게는 localhost 라는 글자 자체가 OS의 기본 로컬 라우팅 테이블에 부팅과 동시에 등재되는데요. localhost 는 호스트명일 뿐이고, 실제로는 IP주소를 사용합니다.")
                    .author("김형섭 (Matthew)")
                    .createdDate(dateFormat.parse("2024-06-05"))
                    .category(Category.NETWORK)
                    .image(S3Info.builder()
                            .imageFileName("TOP9_3.png")
                            .imageFolderName("home/TOP9")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/TOP9/TOP9_3.png")
                            .build())
                    .scrapCount(21)
                    .build();

            Post post4 = Post.builder()
                    .title("[JavaScript] 기본 문법")
                    .content("◉ 자바스크립트로 출력하기 document.write(’내용’); : HTML문서 내부에 출력. 이미 로딩된 문서를 모두 지우고 새로운 내용으로 덮어쓰기 때문에, 실행 시점과 위치에 유의")
                    .author("MINJEE")
                    .createdDate(dateFormat.parse("2023-12-09"))
                    .category(Category.WEB_FRONT)
                    .image(S3Info.builder()
                            .imageFileName("JS_1.png")
                            .imageFolderName("home/JS")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/JS/JS_1.png")
                            .build())
                    .scrapCount(0)
                    .build();

            Post post5 = Post.builder()
                    .title("예제로 이해하는 자바스크립트 최신 문법 11가지")
                    .content("자바스크립트 (Javascript)는 이제까지도 지속적으로 발전하며, 최신 트렌드를 반영한 혁신적인 문법들을 선보이고 있습니다. 이들 현대적인 문법들은 코드의 효율성을 높이며, 가독성을 향상시키는 동시에 코드의 실행 시간을 줄이는 성능상의 이점을 제공합니다.")
                    .author("workee")
                    .createdDate(dateFormat.parse("2024-06-02"))
                    .category(Category.WEB_FRONT)
                    .image(S3Info.builder()
                            .imageFileName("JS_2.png")
                            .imageFolderName("home/JS")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/JS/JS_2.jpg")
                            .build())
                    .scrapCount(0)
                    .build();

            Post post6 = Post.builder()
                    .title("[JavaScript] 최신 문법 정리 (ES6 이후)")
                    .content("자바스크립트의 혁명이라 할수 있는 ECMA Sript 2015(ES6) 이후 추가된 자바스크립트 최신 문법 중 자주 이용할 것 같은 기능들을 추려 정리해보는 시간이다.")
                    .author("Eden")
                    .createdDate(dateFormat.parse("2023-01-20"))
                    .category(Category.WEB_FRONT)
                    .image(S3Info.builder()
                            .imageFileName("JS_3.png")
                            .imageFolderName("home/JS")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/JS/JS_3.png")
                            .build())
                    .scrapCount(0)
                    .build();

            Post post7 = Post.builder()
                    .title("좋은 개발 리더가 되기 위해 고민해본 것들")
                    .content("이번 글에서는 지난 3년간 개인 기여자(Individual Contributor, IC)가 아닌 한 명의 리더로서 좋은 리더란 무엇인지, 또 좋은 리더가 되려면 어떤 역량이 필요한지에 스스로 고민해 봤던 내용에 관해 적어보려고 한다.")
                    .author("Evan Moon")
                    .createdDate(dateFormat.parse("2023-11-27"))
                    .category(Category.ETC)
                    .image(S3Info.builder()
                            .imageFileName("LEADER_1.png")
                            .imageFolderName("home/LEADER")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/LEADER/LEADER_1.png")
                            .build())
                    .scrapCount(0)
                    .build();

            Post post8 = Post.builder()
                    .title("리더로 성장하고 싶은 개발자를 위한 3가지 기술")
                    .content("매니지먼트는 프로젝트 관리, 팀 관리, 프로세스 관리로 구분할 수 있습니다. 첫 번째 프로젝트 관리는 출시 시기와 중점을 둬야 하는 일을 관리하는 기술입니다. 두 번째는 팀 관리, 즉 사람 관리입니다. 세 번째는 프로세스 관리입니다. 진행하는 과정을 관리하는 기술입니다.")
                    .author("골든래빗")
                    .createdDate(dateFormat.parse("2023-05-25"))
                    .category(Category.ETC)
                    .image(S3Info.builder()
                            .imageFileName("LEADER_2.png")
                            .imageFolderName("home/LEADER")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/LEADER/LEADER_2.png")
                            .build())
                    .scrapCount(0)
                    .build();

            Post post9 = Post.builder()
                    .title("개발자 원칙")
                    .content("개발 관련 신간을 구경하다가, 한번쯤 들어봤을 법한 회사에 종사하고 계시는 테크 리더 9분의 경험이 녹아있는 \"개발자 원칙\"이라는 책을 발견하게 되었다. 그리고 각 리더분들이 말하고자 하는 핵심 메시지들이 있고, 이것이 이 책의 목차이다. 나의 기준으로 핵심 메시지 중 가장 관심있는 메시지는 4가지가 있었다.")
                    .author("강현석")
                    .createdDate(dateFormat.parse("2023-01-15"))
                    .category(Category.ETC)
                    .image(S3Info.builder()
                            .imageFileName("LEADER_3.png")
                            .imageFolderName("home/LEADER")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/LEADER/LEADER_3.png")
                            .build())
                    .scrapCount(0)
                    .build();

            Post post10 = Post.builder()
                    .title("한 권으로 끝내는 네트워크 기초")
                    .content("컴퓨터 네트워크는 그 범위에 따라 랜(LAN, Local Area Network)와 왠(WAN, Wide Area Network)로 크게 나뉜다. 랜은 가정이나 사무실 등 하나의 거점 내부를 연결하는 네트워크이며 왠은 거점과 거점을 연결하는 네트워크이다. 즉, 거점 내부를 연결하는 것은 랜이고, 거점과 거점을 연결하는 것은 왠이다.")
                    .author("City_Duck")
                    .createdDate(dateFormat.parse("2023-03-13"))
                    .category(Category.NETWORK)
                    .image(S3Info.builder()
                            .imageFileName("NETWORK_1.png")
                            .imageFolderName("home/NETWORK")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/NETWORK/NETWORK_1.png")
                            .build())
                    .scrapCount(0)
                    .build();

            Post post11 = Post.builder()
                    .title("[네트워크] 네트워크 기초 지식 정리")
                    .content("네트워크 기술이란 서버와 클라이언트의 정보가 오고 가는 다리 역할을 하는 기술의 총칭을 의미한다. 네트워크라는 말은 연결되어 있다라는 뜻으로 컴퓨터 네트워크는 데이터를 케이블에 실어 나르는 것을 의미한다. (무선 LAN은 전파로 데이터를 실어 나른다.)")
                    .author("지푸라기 개발자")
                    .createdDate(dateFormat.parse("2019-09-03"))
                    .category(Category.NETWORK)
                    .image(S3Info.builder()
                            .imageFileName("NETWORK_2.png")
                            .imageFolderName("home/NETWORK")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/NETWORK/NETWORK_2.png")
                            .build())
                    .scrapCount(1)
                    .build();

            Post post12 = Post.builder()
                    .title("네트워크 개념 정리")
                    .content("인터넷 통신방식은 두가지 주체에 의해서 이루어집니다. 클라이언트와 서버입니다. 간단하게 이야기하자면 클라이언트에서 특정 정보를 서버에 요청하면 서버가 그에 해당하는 정보를 다시 클라이언트에게 전달해주는 개념입니다. 다만 인터넷이용자가 한 사람이 아니고 서버도 한개만 존재하는 것이 아닙니다.")
                    .author("적어야 머리에 남는다!")
                    .createdDate(dateFormat.parse("2021-05-02"))
                    .category(Category.NETWORK)
                    .image(S3Info.builder()
                            .imageFileName("NETWORK_3.png")
                            .imageFolderName("home/NETWORK")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/NETWORK/NETWORK_3.png")
                            .build())
                    .scrapCount(1)
                    .build();

            Post post13 = Post.builder()
                    .title("쿠버네티스 리소스 배포와 관리를 위한 ksonnet")
                    .content("쿠버네티스의 리소스 배포는 YAML 스크립트를 기반으로 한다. 하나의 마이크로 서비스를 배포하기 위해서는 최소한 Service, Deployment 두개 이상의 배포 스크립트를 작성해야 하고, 만약에 디스크를 사용한다면 Persistent Volume (aka PV)와 Persistent Volume Claim (PVC)등 추가로 여러 파일을 작성해서 배포해야 한다.")
                    .author("Terry Cho")
                    .createdDate(dateFormat.parse("2019-01-14"))
                    .category(Category.INFRA)
                    .image(S3Info.builder()
                            .imageFileName("CICD_1.png")
                            .imageFolderName("home/CICD")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/CICD/CICD_1.png")
                            .build())
                    .scrapCount(1)
                    .build();

            Post post14 = Post.builder()
                    .title("[AWS] 운영 서버 관리 - 코드 배포")
                    .content("현재 위치 배포 ( 무중단 배포 ) 새롭게 서버를 생성하거나 줄이지 않고 배포하는 것. 예) 절반은 잠시 로드 밸런서에서 제외하고 코드를 배포한 뒤 다시 로드 밸런서에 등록하고 나머지에 똑같은 방법을 진행하는 것")
                    .author("ijnuemik")
                    .createdDate(dateFormat.parse("2020-10-10"))
                    .category(Category.INFRA)
                    .image(S3Info.builder()
                            .imageFileName("CICD_2.png")
                            .imageFolderName("home/CICD")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/CICD/CICD_2.png")
                            .build())
                    .scrapCount(1)
                    .build();

            Post post15 = Post.builder()
                    .title("ArgoCD란? 개념부터 설치 배포까지")
                    .content("ArgoCD란? 기존의 소프트웨어를 배포하고 관리하는 방식은 문제점이 많았다. 인프라 환경을 수동적으로 관리하고, 소프트웨어와 인프라를 따로 관리하는 경우가 많았기에 이로 인해 인프라와 소프트웨어 간의 불일치가 발생하게 되었고, 배포 및 운영 과정에서 문제가 발생할 가능성이 높았다.")
                    .author("wlsdn3004")
                    .createdDate(dateFormat.parse("2023-05-12"))
                    .category(Category.INFRA)
                    .image(S3Info.builder()
                            .imageFileName("CICD_3.png")
                            .imageFolderName("home/CICD")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/CICD/CICD_3.png")
                            .build())
                    .scrapCount(1)
                    .build();

            Post post16 = Post.builder()
                    .title("2024년 주목해야 할 5가지 AI 트렌드")
                    .content("인공지능(AI) 환경은 전례 없는 속도로 진화하며 산업, 사회, 그리고 우리의 일상 생활을 바꾸어 놓고 있습니다. 2024년은 AI 여정에서 결정적인 순간이 될 것이며, 앞으로 지속적으로 영향을 줄 혁신적인 발전이 있을 것입니다. 지금부터 살펴볼 2024년에 주목해야 할 5가지 AI 트렌드는 우리가 마주하고 있는 AI의 향방을 정하는 데 중요한 역할을 할 것입니다.")
                    .author("멜로디 자카리아스(Melody Zacharias)")
                    .createdDate(dateFormat.parse("2024-02-05"))
                    .category(Category.AI)
                    .image(S3Info.builder()
                            .imageFileName("AI_1.png")
                            .imageFolderName("home/AI")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/AI/AI_1.jpg")
                            .build())
                    .scrapCount(1)
                    .build();

            Post post17 = Post.builder()
                    .title("2024년 생성형 AI 트렌드 6가지 미리 보기")
                    .content("불과 얼마 전까지, 영화 <백 투 더 퓨처>에서 자동차 “드로리안(DeLorean)”에 바나나 껍질과 맥주를 연료 삼아 넣는 건 그저 우습기만 한 설정이었습니다. 하지만 오늘날에는 전체 차량의 무려 10%가 전기로만 굴러가죠.1 1년 전까지만 해도, 자연어로 컴퓨터와 대화하는 건 SF에나 나올 일로 여겨졌지만, 이제는 다음 세대부터 개인용 AI 어시스턴트가 없는 삶은 상상할 수 없게 되었습니다.")
                    .author("퀄컴 코리아")
                    .createdDate(dateFormat.parse("2024-02-01"))
                    .category(Category.AI)
                    .image(S3Info.builder()
                            .imageFileName("AI_2.png")
                            .imageFolderName("home/AI")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/AI/AI_2.png")
                            .build())
                    .scrapCount(1)
                    .build();

            Post post18 = Post.builder()
                    .title("2024년 주목해야 할 3가지 인공지능 핵심 트렌드? ...\"소형언어모델, 멀티모달 AI, 과학에서 AI\"")
                    .content("생성 인공지능(Generative AI)은 지난 한 해 동안 놀라운 발전을 이루며 인류의 일상 속에 자리잡았다. 특히 오픈ai 챗GPT, 구글 바드(Bard), 메타 라마(Llama), 마이크로소프트 코파일럿(Microsoft Copilot) 등과 같은 인기 도구를 통해 연구실에서부터 일상생활에 이르기까지 수많은 사람들이 AI를 활용하며 가능성을 확인했다.")
                    .author("전미준")
                    .createdDate(dateFormat.parse("2024-02-14"))
                    .category(Category.AI)
                    .image(S3Info.builder()
                            .imageFileName("AI_3.png")
                            .imageFolderName("home/AI")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/AI/AI_3.jpg")
                            .build())
                    .scrapCount(1)
                    .build();

            Post post19 = Post.builder()
                    .title("제미나이 사용법 총정리 (Gemini Advanced 무료 사용법)")
                    .content("구글의 대화형 AI 제미나이(Gemini)를 사용해 보셨나요? 이 글에서는 제미나이의 주요 기능과 사용법, 그리고 제미나이만이 가능한 활용 사례를 알려드리겠습니다.")
                    .author("김지수")
                    .createdDate(dateFormat.parse("2024-05-01"))
                    .category(Category.AI)
                    .image(S3Info.builder()
                            .imageFileName("GEMINI_1.png")
                            .imageFolderName("home/GEMINI")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/GEMINI/GEMINI_1.png")
                            .build())
                    .scrapCount(1)
                    .build();

            Post post20 = Post.builder()
                    .title("제미나이(Gemini) 사용법 및 활용 사례: 유튜브 요약부터 블로그 글 작성까지")
                    .content("제미나이는 블로그 글 작성을 자동화하는 데 도움이 되는 유용한 도구이지만, 아직 개발 초기 단계이며 완벽하지 않다는 점을 기억해야 합니다. 제미나이를 활용할 때는 반드시 직접 확인하고 수정 후 사용해야 하며, 저작권 침해에 주의해야 합니다.")
                    .author("아야옹이다")
                    .createdDate(dateFormat.parse("2024-05-25"))
                    .category(Category.AI)
                    .image(S3Info.builder()
                            .imageFileName("GEMINI_2.png")
                            .imageFolderName("home/GEMINI")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/GEMINI/GEMINI_2.png")
                            .build())
                    .scrapCount(1)
                    .build();

            Post post21 = Post.builder()
                    .title("구글 생성형 AI 제미나이 Gemini 사용법, ChatGPT와 다른점")
                    .content("ChatGPT에 대항하기 위한 론칭된 구글 바드 (Bard)에서 구글 제미나이 (Gemini)로 이름을 바꾼 구글 인공지능 AI 챗봇 서비스의 다양한 활용방법을 알아보겠습니다. 특히 제미나이는 텍스트 기반 ChatGPT와 달리 이미지, 동영상, 오디오까지 이해 가능한 구글의 생성형 AI로 구글의 모든 서비스에 통합되어 활용되는 강점이 있습니다.")
                    .author("BigPine24")
                    .createdDate(dateFormat.parse("2024-02-29"))
                    .category(Category.AI)
                    .image(S3Info.builder()
                            .imageFileName("GEMINI_3.png")
                            .imageFolderName("home/GEMINI")
                            .imageUrl("https://ticle.s3.ap-northeast-2.amazonaws.com/HOME/GEMINI/GEMINI_3.png")
                            .build())
                    .scrapCount(1)
                    .build();

            Post[] posts = {post1, post2, post3, post4, post5, post6, post7, post8, post9, post10,
                    post11, post12, post13, post14, post15, post16, post17, post18, post19, post20,
                    post21};

            List<Post> postList = new ArrayList<>(Arrays.asList(posts));

            postRepository.saveAll(postList);
        }
    }
}