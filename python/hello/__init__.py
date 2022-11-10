from flask import Flask, jsonify
from flask import Flask, Response
from flask import make_response
from flask import request
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.models import load_model
import pickle as pk
import numpy as np
import json
import 계정매핑_단어_실행_함수_BS as bs
import 계정매핑_단어_실행_함수_PL as pl
import 계정매핑_단어_실행_cashflow as cashflow
import re


app = Flask(__name__)
  


''' 실행
python -m http.server 5000

아래것이 진짜
python app_start
'''

''' 향후 이것을 사용해서 localhost 빼고는 다 막아버릴 것 
@app.before_request
def limit_remote_addr():
    if request.remote_addr != '10.20.30.40':
        abort(403)  # Forbidden
'''

@app.route("/testcoapl", methods = ["GET", "POST"])
def hellopl():
    
    words = request.values.get("coa")
    words2 = json.loads(words)
    realwords = []
    
    for i in words2:

        # 영어,숫자 및 공백 제거.
        text = re.sub('[^가-힣]', '', i)
        realwords.append(text)
    
    print(realwords)
    result = pl.realtest(realwords)
    print(result)
    
    realresult = {}
    for i, word in enumerate(words2):
        realresult[word] = result[i] 
    #print(request.environ)
    #print(request.get_json())
    #print(request.values.get("name"))
    #print(123123123)
    
    #print(result)
    #res = Response("start")
    #res.set_data(result)
    return realresult
    #return make_response(('Tuple Custom Response', 'OK', {"a": result}))

@app.route("/testcoabs", methods = ["GET", "POST"])
def hellobs():
    
    words = request.values.get("coa")
    words2 = json.loads(words)
    realwords = []
    
    for i in words2:

        # 영어,숫자 및 공백 제거.
        text = re.sub('[^가-힣]', '', i)
        realwords.append(text)

    print(realwords)
    result = bs.realtest(realwords)
    print(result)
    
    realresult = {}
    for i, word in enumerate(words2):
        realresult[word] = result[i] 

    return realresult

@app.route("/testcashflow", methods = ["GET", "POST"])
def hellocashflow():
    
    words = request.values.get("coa")
    words2 = json.loads(words)
    realwords = []
    
    for i in words2:

        # 영어,숫자 및 공백 제거.
        text = re.sub('[^가-힣]', '', i)
        realwords.append(text)

    print(realwords)
    result = cashflow.realtest(realwords)
    print(result)
    
    realresult = {}
    for i, word in enumerate(words2):
        realresult[word] = result[i] 

    return realresult
