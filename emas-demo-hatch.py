#!/usr/bin/python2.7
# -*- encoding: UTF-8 -*-

"""
# emas 自动修改脚手架代码替换包名、应用名
# written by chenluan.cht@alibaba-inc.com
"""

import sys
reload(sys)
sys.setdefaultencoding('utf8')
import os
import logging
import shutil
import glob
import ntpath
import fileinput, string
import codecs
from xml.dom import minidom
from os.path import join


source_folder = sys.argv[1]

if (len(sys.argv) == 2) and (source_folder == 'help'):
    print("usage: python emas-demo-hatch.py [source_folder] [app_name] [package_name]") 
    exit()

app_name = sys.argv[2]
app_package = sys.argv[3]

print(">> source folder : " + source_folder)
print(">> app name: " + app_name)
print(">> pakcage name: " + app_package)

exts = ["java", "json", "xml", "gradle", "sh", "properties"]

def check_search_file_valid(filename):
    for ext in exts:
        if ext == os.path.splitext(file)[-1][1:]:
            return True
    return False

# enter source code workspace
os.chdir(source_folder)

# check valid android code folder
if not os.path.exists("app"):
    logging.error("please input a valid android sosurce code folder!")

# replace package name for all files
search = "com.emas.demo"
gradle_file = join("app", "build.gradle")
app_id = "applicationId"
with open(gradle_file, "r") as fi:
    for line in fi:
        if app_id in line:
            line = line.strip()
            text = line.split(" ")
            search = text[1].replace("'", "") # find the current package name

searching_file = ""
for fpathe,dirs,fs in os.walk(os.getcwd()):
    for file in fs:
        searching_file = join(fpathe, file)
        if not check_search_file_valid(searching_file):
            continue
        print("replacing " + searching_file)
        if os.path.isfile(searching_file):
            for line in fileinput.input(searching_file, inplace=1):
                lineno = 0
                # replace search and search
                lineno = string.find(line, search)
                if lineno > 0:
                    line =line.replace(search, app_package)   
                sys.stdout.write(line)

# change app name
print "changing app name..."
res_file = join("app", "src", "main", "res", "values", "strings.xml")
doc = minidom.parse(res_file)
root = doc.documentElement
strings = root.getElementsByTagName("string")
for string in strings:
    if string.getAttribute("name") == "app_name":
        string.firstChild.data = app_name
        break
f = open(res_file, 'w')
writer = codecs.lookup('utf-8')[3](f)
doc.writexml(writer, newl='', indent='', encoding='utf-8')
writer.close()
f.close()

# # rebuild new package folder and copy replaced files into
# print "rebulding new package folder and copy java files..."
# os.chdir(join("app", "src", "main", "java"))
# new_package = app_package.replace(".", os.sep)
# orgin_source_dir = join("com", "taobao", "amnt")
# dst_dir = join(new_package)
# if os.path.exists(dst_dir): 
#     shutil.rmtree(dst_dir)

# shutil.copytree(orgin_source_dir, dst_dir)

# # delete older folder
# shutil.rmtree(join("com", "taobao"))

print "<< done"
