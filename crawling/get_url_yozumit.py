import os,time,io,sys,csv
import requests
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager

sys.stdout = io.TextIOWrapper(sys.stdout.detach(),encoding="utf-8")
sys.stdin = io.TextIOWrapper(sys.stdin.detach(),encoding="utf-8")

service = Service(executable_path='./chromedriver.exe')
driver = webdriver.Chrome(service=service)

# 게시글들의 url을 담아줄 리스트
arr = []
# 기획 : plan
# 디자인 : design
# 개발 : develop
kind = "plan"

#여기서 range의 값을 조절함으로써 다음 페이지로 넘길 수 있음
for i in range(1,3):
    base_url = f'https://yozm.wishket.com/magazine/list/{kind}/?page={i}&sort=&q='
    print(base_url)

    driver.get(base_url)

    time.sleep(1)

    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')

    #list 형태로 tmp에 담김
    tmp = soup.select("body > div.layout > div > div.list-cover > div:nth-child(1) > div > div.flex-box.list-item-box > div.item-main.text900 > a.item-title.link-text.link-underline.text900")
    # print(tmp[0].get('href'))
        
    tmp2 = soup.find_all("a",class_ = "item-title link-text link-underline text900")


    # 링크를 담아두는 리스트 
    
    for tt in tmp2:
        arr.append("https://yozm.wishket.com"+tt.get("href"))
    
print(arr)
post = []

#여기서부턴 파일로 저장하는 파트
folder_name= "yozumIT_post"
if not os.path.exists(folder_name):
    os.makedirs(folder_name)
    
for link in arr:
    print("dddddddddddd",link)
    idx = link.split("/")[-2]
    
    driver.get(link)
    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')
    
    content = soup.find("div",class_="next-news-contents news-highlight-box")
    
    filename = os.path.join(folder_name, f"{idx}.txt")
    with open(filename, 'w', encoding='utf-8') as file:
        file.write(f"IDX : {idx}\n\n{content}")
    # print(content)
    exit()
    

