import os
import requests
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
import time
import sys
import io
import csv
sys.stdout = io.TextIOWrapper(sys.stdout.detach(),encoding="utf-8")
sys.stdin = io.TextIOWrapper(sys.stdin.detach(),encoding="utf-8")

base_url = 'https://velog.io'
# base_url = 'https://velog.io/recent'


# options = webdriver.ChromeOptions()
# options.add_argument("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36 Edg/124.0.0.0")
# driver = webdriver.Chrome(ChromeDriverManager().install())
service = Service(executable_path='./chromedriver.exe')
driver = webdriver.Chrome(service=service)

driver.get(base_url)
# driver.implicitly_wait(3)
time.sleep(10)

# 저장할 폴더 생성
folder_name = 'velog_posts'
if not os.path.exists(folder_name):
    os.makedirs(folder_name)

# headers = {'User-Agent':"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36 Edg/124.0.0.0"}
# 웹 페이지 가져오기
# res = requests.get(base_url,headers=headers)
# html = res.text

html = driver.page_source
# html = response.text

# HTML 파싱
soup = BeautifulSoup(html, 'html.parser')

# 특정 개수의 게시글 URL 찾기

lis = soup.select("li > a")
lis2 = [link.get('href') for link in lis]

print(lis2)

with open("velog_url.csv", 'w',encoding='utf-8-sig') as file:
    writer = csv.writer(file)
    writer.writerow(lis2)


exit()
tmp = soup.select("body > div > div.HomeLayout_block__ZqnqH > div.responsive_mainResponsive___uG64 > div > div.HomeLayout_mainWrapper__raHJK > main > ul")
for a in tmp:
    print(a)
tmp2 = soup.select("body > div > div.HomeLayout_block__ZqnqH > div.responsive_mainResponsive___uG64 > div > div.HomeLayout_mainWrapper__raHJK > main > ul > li:nth-child(2) >a")
print(tmp)
print(soup.find('li', {'class':'PostCard_block__FTMsy'}))
# print(tmp.find_all("li",class_ = "PostCard_block__FTMsy" ))
exit()
for a in soup.select("body > div > div.HomeLayout_block__ZqnqH > div.responsive_mainResponsive___uG64 > div > div.HomeLayout_mainWrapper__raHJK > main > ul"):
    href = a['href']
    if href.startswith('/@'):
        full_url = base_url + href
        if full_url not in links:
            links.append(full_url)
            if len(links) == 10:  # 최대 10개의 게시글만 처리
                break
print(links)
# exit()
# 각 게시글 크롤링 및 파일 저장
for link in links:
    post_response = requests.get(link)
    post_html = post_response.text
    post_soup = BeautifulSoup(post_html, 'html.parser')

    # 게시글 제목과 본문 추출
    title = post_soup.find('h1').text.strip()
    content = post_soup.find('article').text.strip()

    # 파일로 저장
    filename = os.path.join(folder_name, f"{title}.txt")
    with open(filename, 'w', encoding='utf-8') as file:
        file.write(f"Title: {title}\n\n{content}")

print("Crawling completed and data saved.")