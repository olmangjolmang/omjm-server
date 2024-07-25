import os
import requests
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.keys import Keys
from webdriver_manager.chrome import ChromeDriverManager
import pandas as pd
import time
import sys
import io

sys.stdout = io.TextIOWrapper(sys.stdout.detach(), encoding="utf-8")
sys.stdin = io.TextIOWrapper(sys.stdin.detach(), encoding="utf-8")

base_url = 'https://velog.io'

service = Service(executable_path='./chromedriver.exe')
driver = webdriver.Chrome(service=service)

driver.get(base_url)

# 페이지 로딩을 기다립니다.
WebDriverWait(driver, 10).until(
    EC.presence_of_element_located((By.TAG_NAME, "li"))
)

# 스크롤을 내려 추가 콘텐츠를 로드합니다.
for i in range(10):  # 필요에 따라 스크롤 횟수를 조정하세요.
    driver.find_element(By.TAG_NAME, "body").send_keys(Keys.END)
    time.sleep(3)  # 스크롤 후 잠시 대기

html = driver.page_source

# HTML 파싱
soup = BeautifulSoup(html, 'html.parser')

# 특정 개수의 게시글 URL 찾기
post_link_tag = soup.select("li > a")
post_link = [link.get('href') for link in post_link_tag]

post_title_tag = soup.select("li > div > a > h4")
post_title = [title.get_text() for title in post_title_tag]

post_author_tag = soup.select("li > div > a > span > b")
post_author = [author.get_text() for author in post_author_tag]

post_date_tag = soup.select("li > div > div > span:nth-child(1)")
post_date = [date.get_text() for date in post_date_tag]

post_image_tag = soup.select("li > a > div > img")
post_image = [img.get("src")for img in post_image_tag]

print(f"Links: {len(post_link)}")
print(f"Titles: {len(post_title)}")
print(f"Authors: {len(post_author)}")
print(f"Dates: {len(post_date)}")

# 데이터 길이가 같도록 조정
min_length = min(len(post_link), len(post_title), len(post_author), len(post_date))
df = pd.DataFrame({
    "hyper-link": post_link[:min_length],
    "title": post_title[:min_length],
    "author": post_author[:min_length],
    "createdDate": post_date[:min_length],
    "img-url": post_image[:min_length]
})

df.to_csv("./velog_img.csv", encoding='utf-8-sig', index=False)

driver.quit()
