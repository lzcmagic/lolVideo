package com.lzc.exovideo.db.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SourceManager {


    public void getSourceFromBW(){
        new Thread(() -> {



            final StringBuilder builder = new StringBuilder();

            try {
                Document doc = Jsoup.connect("http://www.baiwanzy.com/?m=vod-type-id-1.html").get();

                Element xingVb = doc.getElementsByClass("xing_vb").get(0);
                Elements children = xingVb.children();
                for (Element element:children){
                    Elements select = element.select("a[href]");
                    for (Element link : select) {
                        builder.append("\n").append("Link : ").append(link.attr("href"))
                                .append("\n").append("Text : ").append(link.text());
                    }
                }

            } catch (IOException e) {
                builder.append("Error : ").append(e.getMessage()).append("\n");
            }




        }).start();
    }
}
