# -*- coding: utf-8 -*-
"""
Created on Thu Nov 10 14:24:21 2022

@author: gocho
"""


import re
aa = ['나는abc123난', '나는abc123난', '나는abc123난']
real =[]

for i in aa:

    # 영어,숫자 및 공백 제거.

    text = re.sub('[^가-힣]', '', i)

    real.append(text)
print(real)