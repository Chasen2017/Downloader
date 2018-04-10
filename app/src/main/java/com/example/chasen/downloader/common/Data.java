package com.example.chasen.downloader.common;

import com.example.chasen.downloader.model.FileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chasen on 18-3-26.
 * 数据源
 */

public class Data {

    public static List<FileInfo> mFileInfoList = new ArrayList<>();

    static {
        mFileInfoList.add(new FileInfo(0, "CCTV5.apk", "https://my-notes.oss-cn-beijing.aliyuncs.com/%E4%B8%8B%E8%BD%BD%E5%99%A8/cctv5.jpg",
                "http://ucdl.25pp.com/fs08/2018/03/16/4/2_99edd9fb5d9be392143b7e0f15bf224a.apk?sf=46150608&vh=91225a1e301d881b9d2bac922dffdd76&sh=10&cc=3200745649&appid=5967759&packageid=800595751&md5=62ae2f24e97d004ff81fd5d6a9ba378c&apprd=5967759&pkg=com.cctv.cctv5ultimate&vcode=243&fname=CCTV5&iconUrl=http%3A%2F%2Fandroid%2Dartworks%2E25pp%2Ecom%2Ffs08%2F2018%2F03%2F22%2F11%2F110%5Fefb2a683c92152741de5b61df5a2385a%5Fcon%2Epng",
                0, 0, false));

        mFileInfoList.add(new FileInfo(1, "微信.apk", "https://my-notes.oss-cn-beijing.aliyuncs.com/%E4%B8%8B%E8%BD%BD%E5%99%A8/wechat.jpeg",
                "http://ucdl.25pp.com/fs08/2018/03/05/0/2_e3de1e9e24c390525cb1133fee19032c.apk?sf=63669882&vh=0a1b02e17217f1a916a9f41385cb3dac&sh=10&cc=4265504391&appid=596157&packageid=600600735&md5=02e3b3c4fe0ed8d761b8e54520cc6aab&apprd=596157&pkg=com.tencent.mm&vcode=1280&fname=%E5%BE%AE%E4%BF%A1&iconUrl=http%3A%2F%2Fandroid%2Dartworks%2E25pp%2Ecom%2Ffs08%2F2016%2F11%2F21%2F11%2F106%5F31e1fced509900af481c2395e430a0f7%5Fcon%2Epng",
                0, 0, false));

        mFileInfoList.add(new FileInfo(2, "QQ.apk", "https://my-notes.oss-cn-beijing.aliyuncs.com/%E4%B8%8B%E8%BD%BD%E5%99%A8/QQ.jpg",
                "http://ucdl.25pp.com/fs08/2018/03/21/0/106_63982b301dc4e99f403623466a8e30bc.apk?sf=48416648&vh=0bd0f6ca3a7a417cce220bdf3077025d&sh=10&cc=622935229&appid=566489&packageid=200592604&md5=3379f96c4ed3740c015c2f39c3ed44ac&apprd=566489&pkg=com.tencent.mobileqq&vcode=806&fname=QQ&iconUrl=http%3A%2F%2Fandroid%2Dartworks%2E25pp%2Ecom%2Ffs08%2F2018%2F03%2F22%2F2%2F110%5F3322e99e330c54571245639d3b79a2f8%5Fcon%2Epng",
                0, 0, false));

        mFileInfoList.add(new FileInfo(3, "UC.apk", "https://my-notes.oss-cn-beijing.aliyuncs.com/%E4%B8%8B%E8%BD%BD%E5%99%A8/UC.jpg",
                "http://ucdl.25pp.com/fs08/2018/03/21/1/110_00cb5295408ff6595bd8f5bbadb0e875.apk?sf=55121356&vh=a5d6db509b8238b7bff17faec59d36dd&sh=10&cc=554314776&appid=36557&packageid=600605492&pkgType=1&md5=ccf416c7042eab84198f6db136cc765e&data=dGVzdFRhZz1uZXdgY3BUeXBlPTIxYGJpZD0zMDc3Mzc5MmBkS2V5PTdkZGE5M2MyODcwNjJjZDQyYjZlZDY5OTVhYWFhZjRmYGlLZXk9MWFhMjI3MmIwOWYxMWJmNTEyMzYzMTA5ZGI4ZjFkOGU&apprd=&fname=UC%E6%B5%8F%E8%A7%88%E5%99%A8&iconUrl=http%3A%2F%2Fandroid%2Dartworks%2E25pp%2Ecom%2Ffs08%2F2018%2F03%2F22%2F7%2F110%5F012480abd2bc8ffe04dfcd0d5959a21b%5Fcon%2Epng",
                0, 0, false));

        mFileInfoList.add(new FileInfo(4, "有道云笔记.apk", "https://my-notes.oss-cn-beijing.aliyuncs.com/%E4%B8%8B%E8%BD%BD%E5%99%A8/note.jpg",
                "http://ucdl.25pp.com/fs08/2018/03/21/1/110_32a4ef16e42355e00a022ddcd18d7ce2.apk?sf=52694013&vh=d2ebf8ffa5785c445bb4dcca4aa78a59&sh=10&cc=2963034697&appid=370055&packageid=800596899&md5=bad64b589733e3d18521ad468d145ab2&apprd=370055&pkg=com.youdao.note&vcode=88&fname=%E6%9C%89%E9%81%93%E4%BA%91%E7%AC%94%E8%AE%B0&iconUrl=http%3A%2F%2Fandroid%2Dartworks%2E25pp%2Ecom%2Ffs08%2F2018%2F03%2F21%2F3%2F110%5F7b04fe9be3ceb00e777b1a6b3d834db5%5Fcon%2Epng",
                0, 0, false));

        mFileInfoList.add(new FileInfo(5, "网易云音乐.apk", "https://my-notes.oss-cn-beijing.aliyuncs.com/%E4%B8%8B%E8%BD%BD%E5%99%A8/music.jpg",
                "http://ucdl.25pp.com/fs08/2018/03/09/2/2_e27d5f171e0e3509834a3423880b8a0b.apk?sf=43118869&vh=d36d9ca9f5cab268f05f431f5515fa29&sh=10&cc=2937509492&appid=293217&packageid=200589085&md5=1926e479d2511b883b448bc48cba6ebb&apprd=293217&pkg=com.netease.cloudmusic&vcode=115&fname=%E7%BD%91%E6%98%93%E4%BA%91%E9%9F%B3%E4%B9%90&iconUrl=http%3A%2F%2Fandroid%2Dartworks%2E25pp%2Ecom%2Ffs08%2F2018%2F03%2F26%2F6%2F110%5Fa2c9036c3dcf42f13ce00d7183bd3c78%5Fcon%2Epng",
                0, 0, false));

        mFileInfoList.add(new FileInfo(6, "支付宝.apk", "https://my-notes.oss-cn-beijing.aliyuncs.com/%E4%B8%8B%E8%BD%BD%E5%99%A8/alipay.jpg",
                "http://ucdl.25pp.com/fs08/2018/03/08/5/110_2ddc5c45ab98808f83d1f2e8166cca2c.apk?sf=59697160&vh=7202c0a173199527182fa716a0764898&sh=10&cc=1548311993&appid=279979&packageid=400584017&md5=2548203d4cd2846e944add93b4cb8ff7&apprd=&fname=%E6%94%AF%E4%BB%98%E5%AE%9D&iconUrl=http%3A%2F%2Fandroid%2Dartworks%2E25pp%2Ecom%2Ffs08%2F2018%2F03%2F06%2F1%2F110%5Fb19f454b0b50d8aa9ea0290ee6b15422%5Fcon%2Epng",
                0, 0, false));

        mFileInfoList.add(new FileInfo(7, "淘宝.apk", "https://my-notes.oss-cn-beijing.aliyuncs.com/%E4%B8%8B%E8%BD%BD%E5%99%A8/taobao.jpg",
                "http://ucdl.25pp.com/fs08/2018/03/01/8/110_2f244e90ba0d6871984e3daee8597a91.apk?sf=80220527&vh=4b5da8ebe4e794f15f6a5245d628ab39&sh=10&cc=1378051959&appid=32267&packageid=400582555&md5=00ba32ce0a24c8395bd647ebe1fe9ffa&apprd=&fname=%E6%B7%98%E5%AE%9D&iconUrl=http%3A%2F%2Fandroid%2Dartworks%2E25pp%2Ecom%2Ffs08%2F2018%2F03%2F01%2F5%2F110%5F2e0a4c31b76fefb65c451191cfd22daf%5Fcon%2Epng",
                0, 0, false));

        mFileInfoList.add(new FileInfo(8, "超级课程表.apk", "https://my-notes.oss-cn-beijing.aliyuncs.com/%E4%B8%8B%E8%BD%BD%E5%99%A8/kechengbiao.jpg",
                "http://ucdl.25pp.com/fs08/2018/03/06/3/102_3bdb715e6863219a2816fa35378e69f0.apk?sf=36327866&vh=51761fa004579de3bc90ae5cafb6c99c&sh=10&cc=1143699808&appid=33231&packageid=800592498&md5=b04939ed19729b21a2d70a7b09f55c83&apprd=33231&pkg=com.xtuone.android.syllabus&vcode=115&fname=%E8%B6%85%E7%BA%A7%E8%AF%BE%E7%A8%8B%E8%A1%A8&iconUrl=http%3A%2F%2Fandroid%2Dartworks%2E25pp%2Ecom%2Ffs08%2F2018%2F03%2F17%2F11%2F110%5F3c73d750bff27e887e3afbcb2b94dfb0%5Fcon%2Epng",
                0, 0, false));

        mFileInfoList.add(new FileInfo(9, "百度网盘.apk", "https://my-notes.oss-cn-beijing.aliyuncs.com/%E4%B8%8B%E8%BD%BD%E5%99%A8/baiduwangpan.jpg",
                "http://ucdl.25pp.com/fs08/2018/03/01/0/106_3e1ed44f6a95684ce84ccbcc745886b6.apk?sf=32496284&vh=b8c42910a99a37d6deff7455214cbd48&sh=10&cc=1396364401&appid=280851&packageid=400582569&md5=7c483e372b2414653a8721e891687033&apprd=280851&pkg=com.baidu.netdisk&vcode=579&fname=%E7%99%BE%E5%BA%A6%E7%BD%91%E7%9B%98&iconUrl=http%3A%2F%2Fandroid%2Dartworks%2E25pp%2Ecom%2Ffs08%2F2018%2F03%2F05%2F6%2F110%5F053ce47fcdedff333ee56e64d51fe7b7%5Fcon%2Epng",
                0, 0, false));
    }
}
