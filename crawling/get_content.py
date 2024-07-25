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
import pandas as pd

sys.stdout = io.TextIOWrapper(sys.stdout.detach(),encoding="utf-8")
sys.stdin = io.TextIOWrapper(sys.stdin.detach(),encoding="utf-8")

# 크롬 가져오기
service = Service(executable_path=ChromeDriverManager().install())
driver = webdriver.Chrome(service=service)

FILE_PATH = "./yozum_it_with_category.csv"
df = pd.read_csv(FILE_PATH, encoding='utf-8-sig')

df["content"] = ""

def split_content(content, chunk_size=32767):  # Excel cell limit is 32767 characters
    return [content[i:i + chunk_size] for i in range(0, len(content), chunk_size)]

for idx, base_url in enumerate(df['hyper-link']):
    print(idx,base_url)
    driver.get(base_url)
    time.sleep(5)  # 페이지 로딩 대기

    # 스크롤 다운
    last_height = driver.execute_script("return document.body.scrollHeight")
    scroll_pause_time = 3  # 스크롤 후 로딩 대기 시간

    while True:
        driver.execute_script("window.scrollTo(0, document.body.scrollHeight);") # 500 픽셀씩 스크롤
        time.sleep(scroll_pause_time)  # 스크롤 후 로딩 대기

        new_height = driver.execute_script("return document.body.scrollHeight")
        if new_height == last_height:
            break
        last_height = new_height

    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')

    content_tag = soup.select_one("#root > div:nth-child(2) > div:nth-child(4) > div > div") # velog
    #root > div.sc-dPiLbb.sc-bBHHxi.kTIDXm > div.sc-jKTccl.hiCda > div > div

    # content_tag = soup.select_one("div.content-container > div.next-news-contents.news-highlight-box") #yozum
    # body > div.layout > div.container-wrapper > div.content-container-wrapper > div.content-container > div.next-news-contents.news-highlight-box
    content = str(content_tag) if content_tag else ""
    content_chunks = split_content(content)
    for i, chunk in enumerate(content_chunks):
        column_name = f'content_part_{i+1}'
        if column_name not in df.columns:
            df[column_name] = ""
        df.loc[idx, column_name] = chunk

df.to_csv("./final_velog2.csv", encoding='utf-8-sig', index=False)

driver.quit()
