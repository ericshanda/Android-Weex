#!/bin/bash

#脚手架生成脚本
#脚本依赖：
#1. mac系统必须使用GUN的sed，使用命令安装gnu-sed到/usr/local/bin/sed，替换系统FreeBSD的sed. brew install gnu-sed --with-default-names
#   安装完成后执行sed应该看到“GNU sed home page”几个关键字，如果不是说明还是使用的macos系统/usr/bin/sed，需要将/usr/local/bin加入$PATH且在/usr/bin之前
#2. 帮助文档 ./gen_scaffold.sh -h
#3. 命令示例：./gen_scaffold.sh -CUSTOM_REPOSITORY_HOST maven.com -CUSTOM_REPOSITORY_USERNAME user -CUSTOM_REPOSITORY_PASSWORD 'pwd$1#2&3/4' -SDK_CONFIG_APP_KEY appkey -SDK_CONFIG_APP_SECRET appsecret -SDK_CONFIG_USE_HTTP false -SDK_CONFIG_ACCS_DOMAIN accs.com -SDK_CONFIG_MTOP_DOMAIN mtop.com -SDK_CONFIG_ZCACHE_PREFIX http://zcache.com/prefex -SDK_CONFIG_HA_OSS_BUCKET ha-oss-bucket -SDK_CONFIG_HA_ADASH_DOMAIN adash.com -APP_NAME myapp -MAVEN_BASE_GROUP com.my -SDK_CONFIG_CHANNEL_ID 1001@DemoApp_Android_1.0 -WEEX_UI_SDK 1 -WEEX_BUSINESS_COMPONENTS 1 -WEEX_PUBLIC_REPOSITORY_URL http://weex.com/prefix  -WEEX_REPOSITORY_USERNAME weex_user -WEEX_REPOSITORY_PASSWORD weex_pwd -WEEX_BUSINESS_CHARTS 1 -WEEX_PAGE_TAB_SIZE 5

set -e

#应用名，包名（发布坐标）修改
APP_NAME=""
MAVEN_BASE_GROUP=""

#客户定制，需从控制台系统配置读取
CUSTOM_REPOSITORY_HOST=""
CUSTOM_REPOSITORY_USERNAME=""
CUSTOM_REPOSITORY_PASSWORD=""

#SDK_CONFIG系统配置
SDK_CONFIG_APP_KEY=""
SDK_CONFIG_APP_SECRET=""
SDK_CONFIG_CHANNEL_ID=""
SDK_CONFIG_USE_HTTP=""
SDK_CONFIG_ACCS_DOMAIN=""
SDK_CONFIG_MTOP_DOMAIN=""
SDK_CONFIG_ZCACHE_PREFIX=""  # ZCache.URL
SDK_CONFIG_ORANGE_DOMAIN=""  # RemoteConfig.Domain
SDK_CONFIG_HA_OSS_BUCKET=""  #HA.OSSBucketName
SDK_CONFIG_HA_ADASH_DOMAIN=""  # HA.UniversalHost
SDK_CONFIG_HA_PUBLIC_KEY=""    # HA.RSAPublicKey

#Weex外围SDK配置，传入非""表示启用
WEEX_UI_SDK=""
WEEX_BUSINESS_COMPONENTS=""
WEEX_BUSINESS_CHARTS=""

#WEEX商业组件仓库(仅勾选了商业组件或商业图表选项的WEEX脚手架需要添加)
WEEX_PUBLIC_REPOSITORY_URL=""
WEEX_REPOSITORY_USERNAME=""
WEEX_REPOSITORY_PASSWORD=""

#Weex Native页面配置
WEEX_PAGE_TAB_SIZE=""

#脚手架类型
SCAFFOLD_TYPE=7

#打印帮助文档
printHelp() {
    echo "scaffold generate script."
    echo
    echo "options:"
    echo "   -h help."

    echo "   -APP_NAME                          自定义应用名。必填"
    echo "   -MAVEN_BASE_GROUP                  发布坐标/包名。必填"

    echo "   -CUSTOM_REPOSITORY_HOST            gradle.properties Maven产物仓库域名（不要带http）。必填"
    echo "   -CUSTOM_REPOSITORY_USERNAME        gradle.properties Maven产物仓库用户名。必填"
    echo "   -CUSTOM_REPOSITORY_PASSWORD        gradle.properties Maven产物仓库密码。必填"

    echo "   -SDK_CONFIG_APP_KEY                AppKey，从控制台读取。必填"
    echo "   -SDK_CONFIG_APP_SECRET             AppSecret，从控制台读取。必填"
    echo "   -SDK_CONFIG_CHANNEL_ID             ChannelID。必填"
    echo "   -SDK_CONFIG_USE_HTTP               UseHTTP，从控制台读取。可选"
    echo "   -SDK_CONFIG_ACCS_DOMAIN            ACCS Domain, 从控制台读取。可选"
    echo "   -SDK_CONFIG_MTOP_DOMAIN            MTOP Domain，从控制台读取。可选"
    echo "   -SDK_CONFIG_ZCACHE_PREFIX          ZCache URL，从控制台读取。可选"
    echo "   -SDK_CONFIG_ORANGE_DOMAIN          RemoteConfig Domain，从控制台读取。可选"
    echo "   -SDK_CONFIG_HA_OSS_BUCKET          HA.OSSBucketName，从控制台读取。可选"
    echo "   -SDK_CONFIG_HA_ADASH_DOMAIN        HA.UniversalHost，从控制台读取。可选"
    echo "   -SDK_CONFIG_HA_PUBLIC_KEY          HA.RSAPublicKey，从控制台读取。可选"

    echo "   -WEEX_UI_SDK                       启用weex-ui SDK时设置为1。可选"

    echo "   -WEEX_BUSINESS_COMPONENTS          启用商业组件SDK时设置为1。可选"
    echo "   -WEEX_PUBLIC_REPOSITORY_URL        gradle.properties WEEX商业组件仓库URL，选中商业组件时必选"
    echo "   -WEEX_REPOSITORY_USERNAME          gradle.properties WEEX商业组件仓库用户名，选中商业组件时必选"
    echo "   -WEEX_REPOSITORY_PASSWORD          gradle.properties WEEX商业组件仓库密码，选中商业组件时必选"

    echo "   -WEEX_BUSINESS_CHARTS              启用商业图表SDK时设置为1。可选"
    echo "   -WEEX_PAGE_TAB_SIZE                Weex首页Tab数量，0表示首页非weex，1表示为单页结构，2-5为tab页结构。可选"
    echo "   -SCAFFOLD_TYPE                     表示脚手架类型，int类型，1->native，2->weex，4->H5，例如同时勾选weex和H5即为2|4 = 6"

    echo
}

escapeSpecialChars() {
    # &替换为\&
    TEMP=${1//&/\\&}
    # /替换为\/
    TEMP=${TEMP//\//\\/}
    echo $TEMP
}

checkParameters() {
    echo "starting check parameters ..."
    REQUIRED_CONFIGS=(CUSTOM_REPOSITORY_HOST CUSTOM_REPOSITORY_USERNAME CUSTOM_REPOSITORY_PASSWORD
        SDK_CONFIG_APP_KEY SDK_CONFIG_APP_SECRET SDK_CONFIG_CHANNEL_ID
        APP_NAME MAVEN_BASE_GROUP
    )
    for config in ${REQUIRED_CONFIGS[@]}
    do
        if [ "${!config}" == "" ]; then
            echo "$config is required."
            exit 1
        fi
    done
    echo "check parameters done."
}

modifyGradleProperties() {
    echo "starting modify gradle.properties ..."
    # 替换整行
    if [ "$CUSTOM_REPOSITORY_HOST" != "" ]; then
        sed -i "s/CUSTOM_REPOSITORY_HOST =.*/CUSTOM_REPOSITORY_HOST = http:\/\/$CUSTOM_REPOSITORY_HOST/g" gradle.properties
    fi

    if [ "$CUSTOM_REPOSITORY_USERNAME" != "" ]; then
        sed -i "s/CUSTOM_REPOSITORY_USERNAME =.*/CUSTOM_REPOSITORY_USERNAME = $CUSTOM_REPOSITORY_USERNAME/g" gradle.properties
    fi

    if [ "$CUSTOM_REPOSITORY_PASSWORD" != "" ]; then
        sed -i "s/CUSTOM_REPOSITORY_PASSWORD =.*/CUSTOM_REPOSITORY_PASSWORD = $CUSTOM_REPOSITORY_PASSWORD/g" gradle.properties
    fi
    echo "modify gradle.properties done."
}

modifyGradleWrapperProperties() {
    echo "starting modify gradle-wrapper.properties ..."
    # 替换整行
    if [ "$CUSTOM_REPOSITORY_HOST" != "" ]; then
        sed -i "s/distributionUrl=.*/distributionUrl=http:\/\/${CUSTOM_REPOSITORY_HOST}\/repository\/maven-releases\/com\/gradle\/4.1\/gradle-4.1-all.zip"/ ./gradle/wrapper/gradle-wrapper.properties
    fi
    echo "modify gradle-wrapper.properties done."
}


modifyNativeSDk() {
    echo "starting modify native SDK ..."
    SDK_PATH="app/src/main/assets/aliyun-emas-services.json"
    #替换整行
    if [ "$SDK_CONFIG_APP_KEY" != "" ]; then
        sed -i "s/\"AppKey\".*/\"AppKey\": \"$SDK_CONFIG_APP_KEY\",/g" $SDK_PATH
    fi

    if [ "$SDK_CONFIG_APP_SECRET" != "" ]; then
        sed -i "s/\"AppSecret\".*/\"AppSecret\": \"$SDK_CONFIG_APP_SECRET\",/g" $SDK_PATH
    fi

    if [ "$SDK_CONFIG_CHANNEL_ID" != "" ]; then
        sed -i "s/\"ChannelID\".*/\"ChannelID\": \"$SDK_CONFIG_CHANNEL_ID\",/g" $SDK_PATH
    fi

    if [ "$SDK_CONFIG_USE_HTTP" != "" ]; then
        sed -i "s/\"UseHTTP\".*/\"UseHTTP\": \"$SDK_CONFIG_USE_HTTP\",/g" $SDK_PATH
    fi

    if [ "$SDK_CONFIG_HA_OSS_BUCKET" != "" ]; then
        sed -i "s/\"OSSBucketName\".*/\"OSSBucketName\": \"$SDK_CONFIG_HA_OSS_BUCKET\",/g" $SDK_PATH
    fi

    if [ "$SDK_CONFIG_HA_ADASH_DOMAIN" != "" ]; then
        sed -i "s/\"UniversalHost\".*/\"UniversalHost\": \"$SDK_CONFIG_HA_ADASH_DOMAIN\",/g" $SDK_PATH
    fi

    if [ "$SDK_CONFIG_HA_PUBLIC_KEY" != "" ]; then
        #注意行尾没有,
        sed -i "s/\"RSAPublicKey\".*/\"RSAPublicKey\": \"$SDK_CONFIG_HA_PUBLIC_KEY\"/g" $SDK_PATH
    fi
    
    #替换匹配的下一行
    if [ "$SDK_CONFIG_ACCS_DOMAIN" != "" ]; then
        #如果"ACCS"被匹配，则n命令移动到匹配行的下一行，替换这一行的Domain信息
        sed -i "/\"ACCS\"/{n; s/\"Domain\".*/\"Domain\": \"$SDK_CONFIG_ACCS_DOMAIN\"/g; }" $SDK_PATH
    fi

    if [ "$SDK_CONFIG_MTOP_DOMAIN" != "" ]; then
        sed -i "/\"MTOP\"/{n; s/\"Domain\".*/\"Domain\": \"$SDK_CONFIG_MTOP_DOMAIN\"/g; }" $SDK_PATH
    fi

    if [ "$SDK_CONFIG_ZCACHE_PREFIX" != "" ]; then
        sed -i "/\"ZCache\"/{n; s/\"URL\".*/\"URL\": \"$SDK_CONFIG_ZCACHE_PREFIX\"/g; }" $SDK_PATH
    fi

    if [ "$SDK_CONFIG_ORANGE_DOMAIN" != "" ]; then
        sed -i "/\"RemoteConfig\"/{n; s/\"Domain\".*/\"Domain\": \"$SDK_CONFIG_ORANGE_DOMAIN\"/g; }" $SDK_PATH
    fi
    echo "modify native SDK done."
}

modifyH5Scaffold() {
    echo "start modify H5 SDK dependency ..."

    GRADLE_PATH="app/build.gradle"
    LAYOUT_WEB_MAIN="app/src/main/res/layout/activity_webview_sample.xml"
    FOLDER_WEB_CONTAINER="app/src/main/java/com/taobao/demo/webview"

    #如果没有H5类型，则去掉H5依赖（原项目中默认是带H5的）
    if [ $[ $SCAFFOLD_TYPE&4 ] -ne 4 ]; then
        echo "scaffold type not include H5"
        #删除部分H5示例代码
        rm $LAYOUT_WEB_MAIN
        rm -rf $FOLDER_WEB_CONTAINER
        #去除依赖
        sed -i "/com.emas.hybrid:emas-hybrid-android/{ s/compile/\/\/compile/g }" $GRADLE_PATH
        #去除初始化
        mv app/src/HybridInit.java.out app/src/main/java/com/taobao/demo/HybridInit.java

    fi

    echo "modify H5 scaffold done."

}


modifyWeexSDK() {
    echo "start modify WEEX SDK ..."

    GRADLE_PATH="app/build.gradle"

    #weex-ui sdk（bindingx)
    if [ "$WEEX_UI_SDK" == "" ]; then
        #注释：将complie替换成 //complie
        sed -i "/com.alibaba.android:bindingx-core/{ s/compile/\/\/compile/g }" $GRADLE_PATH
        sed -i "/com.alibaba.android:bindingx_weex_plugin/{ s/compile/\/\/compile/g }" $GRADLE_PATH
    else
        #取消注释：将//替换掉成""
        sed -i "/com.alibaba.android:bindingx-core/{ s/\///g }" $GRADLE_PATH
        sed -i "/com.alibaba.android:bindingx_weex_plugin/{ s/\///g }" $GRADLE_PATH
        mv app/src/BindingXInit.java.out app/src/main/java/com/taobao/demo/BindingXInit.java
    fi

    #商业组件SDK
    if [ "$WEEX_BUSINESS_COMPONENTS" == "" ]; then
        #注释：将complie替换成 //complie
        sed -i "/com.alibaba.emas.xcomponent:xbase/{ s/compile/\/\/compile/g }" $GRADLE_PATH
        sed -i "/com.emas.weex:weex-libs/{ s/compile/\/\/compile/g }" $GRADLE_PATH
        sed -i "/com.emas.weex:weex-base/{ s/compile/\/\/compile/g }" $GRADLE_PATH
        sed -i "/com.emas.weex:fingerprint/{ s/compile/\/\/compile/g }" $GRADLE_PATH
        sed -i "/com.emas.weex:patternlock/{ s/compile/\/\/compile/g }" $GRADLE_PATH
        sed -i "/com.alibaba.emas.xcomponent:umeng-social/{ s/compile/\/\/compile/g }" $GRADLE_PATH
        sed -i "/com.amap.api:location/{ s/compile/\/\/compile/g }" $GRADLE_PATH
    else
        #取消注释：将/替换掉成""
        sed -i "/com.alibaba.emas.xcomponent:xbase/{ s/\///g }" $GRADLE_PATH
        sed -i "/com.emas.weex:weex-libs/{ s/\///g }" $GRADLE_PATH
        sed -i "/com.emas.weex:weex-base/{ s/\///g }" $GRADLE_PATH
        sed -i "/com.emas.weex:fingerprint/{ s/\///g }" $GRADLE_PATH
        sed -i "/com.emas.weex:patternlock/{ s/\///g }" $GRADLE_PATH
        sed -i "/com.alibaba.emas.xcomponent:umeng-social/{ s/\///g }" $GRADLE_PATH
        sed -i "/com.amap.api:location/{ s/\///g }" $GRADLE_PATH

        #选中商业组件需要同时修改gradle.properties信息
        if [ "$WEEX_PUBLIC_REPOSITORY_URL" != "" ]; then
            sed -i "s/WEEX_PUBLIC_REPOSITORY_URL =.*/WEEX_PUBLIC_REPOSITORY_URL = $WEEX_PUBLIC_REPOSITORY_URL/g" gradle.properties
        fi

        if [ "$WEEX_REPOSITORY_USERNAME" != "" ]; then
            sed -i "s/WEEX_REPOSITORY_USERNAME =.*/WEEX_REPOSITORY_USERNAME = $WEEX_REPOSITORY_USERNAME/g" gradle.properties
        fi

        if [ "$WEEX_REPOSITORY_PASSWORD" != "" ]; then
            sed -i "s/WEEX_REPOSITORY_PASSWORD =.*/WEEX_REPOSITORY_PASSWORD = $WEEX_REPOSITORY_PASSWORD/g" gradle.properties
        fi
    fi

    #商业图表SDK
    if [ "$WEEX_BUSINESS_CHARTS" == "" ]; then
        #注释：将complie替换成 //complie
        sed -i "/org.weex.plugin.weexacechart/{ s/compile/\/\/compile/g }" $GRADLE_PATH
        sed -i "/com.alibaba.dt:acechart/{ s/compile/\/\/compile/g }" $GRADLE_PATH
        sed -i "/com.android.support:support-annotations/{ s/compile/\/\/compile/g }" $GRADLE_PATH
        sed -i "/compile .*org.weex.plugin:processor/{ s/compile/\/\/compile/g }" $GRADLE_PATH
        sed -i "/annotationProcessor .*org.weex.plugin:processor/{ s/annotationProcessor/\/\/annotationProcessor/g }" $GRADLE_PATH
    else
        #取消注释：将/替换掉成""
        sed -i "/org.weex.plugin.weexacechart/{ s/\///g }" $GRADLE_PATH
        sed -i "/com.alibaba.dt:acechart/{ s/\///g }" $GRADLE_PATH
        sed -i "/com.android.support:support-annotations/{ s/\///g }" $GRADLE_PATH
        sed -i "/compile .*org.weex.plugin:processor/{ s/\///g }" $GRADLE_PATH
        sed -i "/annotationProcessor .*org.weex.plugin:processor/{ s/\///g }" $GRADLE_PATH
        mv app/src/WeexChartInit.java.out app/src/main/java/com/taobao/demo/WeexChartInit.java
    fi
    echo "modify WEEX SDK done."
}

modifyWeexNativePage() {
    echo "start modify WEEX native page ..."
    if [ "$WEEX_PAGE_TAB_SIZE" != "" ]; then
        sed -i "s/\"TabSize\".*/\"TabSize\": \"$WEEX_PAGE_TAB_SIZE\",/g" app/src/main/assets/weex-container.json
    fi
    echo "modify Weex native page done."
}

modifyScaffoldType() {
    echo "start modify scaffold type ..."
    if [ "$SCAFFOLD_TYPE" != "" ]; then
        sed -i "s/\"ScaffoldType\".*/\"ScaffoldType\": \"$SCAFFOLD_TYPE\",/g" app/src/main/assets/weex-container.json
    fi
    echo "done"
}

modifyPackageName() {
    echo "start modify package name ..."
    python emas-demo-hatch.py . $APP_NAME $MAVEN_BASE_GROUP > /dev/null
    echo "modify package name done."
}

while [ $# -gt 0 ];do
    case $1 in
        -h)
            printHelp
            exit 0
            ;;
        -*)
            #字符串截取: $1截取-
            param_name=${1#-}
            shift
            TEMP=`escapeSpecialChars "${1}"`
            eval $param_name='$TEMP'
            shift
            ;;
    esac
done


#0. 参数检查
checkParameters

#1. 修改gradle.properties
modifyGradleProperties

#2. 修改gradle-wrapper.properties
modifyGradleWrapperProperties

#3. native sdk配置修改
modifyNativeSDk

#4. 发布包名修改
modifyPackageName

#5. weex外围sdk相关配置
modifyWeexSDK

#6. Weex Native页面配置
modifyWeexNativePage

#7. 脚手架类型配置
modifyScaffoldType

#8. 更新H5依赖
modifyH5Scaffold
