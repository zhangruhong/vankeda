package com.wugao.vankeda.test;

import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

public class HanLPTest {

	public static void main(String[] args) {
//		String content = "大益茶 勐海茶厂普洱茶 7572熟茶云南七子饼357g茶叶2017年1701批";
		String content = "波斯顿男士爽肤水补水保湿清爽控油收缩毛孔紧肤须后水喷雾护肤品";
		List<String> keywordList = HanLP.extractKeyword(content, 10);
		System.out.println(keywordList);
	}
}
