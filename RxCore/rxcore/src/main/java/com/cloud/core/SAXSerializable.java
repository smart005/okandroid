/**
 * @Title:  SAXSerializable.java
 * @Description:  xml解析
 * @author:  lijinghuan
 * @data:  2015年5月4日 上午7:11:43
 */
package com.cloud.core;

import android.content.Context;
import android.text.TextUtils;

import com.cloud.core.logger.Logger;
import com.cloud.core.utils.GlobalUtils;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXSerializable extends DefaultHandler {

	/**
	 * 键值列表(键与xml节点名或属性名相同)
	 */
	private HashMap<String, String> KVList = new HashMap<String, String>();
	private List<HashMap<String, String>> datalist = new ArrayList<HashMap<String, String>>();
	private HashSet<String> nodes = new HashSet<String>();
	private String itemTag = "";
	private Boolean isobj = false;
	private StringBuffer sb = new StringBuffer();

	/**
	 * @param 设置itemTag
	 */
	public void setItemTag(String itemTag) {
		this.itemTag = itemTag;
	}

	/**
	 * SAX序列构造函数
	 * 
	 * @param notNodeNames
	 *            不获取值的xml节点名集合
	 */
	public SAXSerializable(String[] notNodeNames) {
		if (!ObjectJudge.isNullOrEmpty(notNodeNames)) {
			for (String item : notNodeNames) {
				if (!nodes.contains(item)) {
					nodes.add(item);
				}
			}
		}
	}

	/**
	 * SAX序列构造函数
	 * 
	 * @param notNodeNames
	 *            不获取值的xml节点名集合
	 * @param isObjEntity
	 *            是否对象实体<br/>
	 *            true:每个节点解析成SAXSerializableEntity对象;<br/>
	 *            false:解析成HashMap< String, String>对象;
	 */
	public SAXSerializable(String[] notNodeNames, Boolean isObjEntity) {
		if (!ObjectJudge.isNullOrEmpty(notNodeNames)) {
			for (String item : notNodeNames) {
				if (!nodes.contains(item)) {
					nodes.add(item);
				}
			}
		}
		isobj = isObjEntity;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		if (KVList == null) {
			KVList = new HashMap<String, String>();
		} else {
			KVList.clear();
		}
		if (datalist == null) {
			datalist = new ArrayList<HashMap<String, String>>();
		} else {
			datalist.clear();
		}
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		isobj = false;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
							 Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		sb.setLength(0);
		if (TextUtils.equals(itemTag, localName)) {
			KVList = new HashMap<String, String>();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		String value = sb.toString();
		if (isobj) {
			KVList.put(localName, value);
			if (TextUtils.equals(itemTag, localName)) {
				datalist.add(KVList);
			}
		} else {
			if (!ObjectJudge.isNullOrEmpty(nodes) && !nodes.contains(localName)) {
				KVList.remove(localName);
				KVList.put(localName, value);
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		String value = new String(ch, start, length).trim();
		if (!TextUtils.isEmpty(value)) {
			sb.append(value);
		}
	}

	/**
	 * 获取数据列表
	 * 
	 * @param source
	 *            xml资源
	 * @return
	 */
	private XMLReader getXMLReader(InputSource source) {
		try {
			// 实例化一个SAXParserFactory对象
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = null;
			// 实例化SAXParser对象，创建XMLReader对象，解析器
			parser = factory.newSAXParser();
			XMLReader xmlreader = parser.getXMLReader();
			// 解析器注册事件
			xmlreader.setContentHandler(this);
			return xmlreader;
		} catch (Exception ex) {
			Logger.L.error("获取xml适配器异常[InputSource:source]", ex);
			return null;
		}
	}

	public HashMap<String, String> getList(InputSource source) {
		try {
			XMLReader xmlreader = getXMLReader(source);
			if (xmlreader != null) {
				xmlreader.parse(source);
			}
		} catch (Exception ex) {
			Logger.L.error("获取数据列表异常[InputSource:source]", ex);
		}
		return this.KVList;
	}

	/**
	 * 获取数据列表
	 * 
	 * @param source
	 *            xml资源
	 * @return
	 */
	public List<HashMap<String, String>> getEntityList(InputSource source) {
		try {
			XMLReader xmlreader = getXMLReader(source);
			if (xmlreader != null) {
				xmlreader.parse(source);
			}
		} catch (Exception ex) {
			Logger.L.error("get datalist error:", ex);
		}
		return this.datalist;
	}

	public <T> T parseT(HashMap<String, String> source, Class<T> cls) {
		T t = null;
		try {
			t = cls.newInstance();
			if (!ObjectJudge.isNullOrEmpty(source)) {
				for (Map.Entry<String, String> item : source.entrySet()) {
					GlobalUtils.setPropertiesValue(t, item.getKey(),
							item.getValue());
				}
			}
		} catch (InstantiationException e) {
			Logger.L.error("xml parase error:", e);
		} catch (IllegalAccessException e) {
			Logger.L.error("xml parase error:", e);
		}
		return t;
	}

	/**
	 * 获取数据列表
	 * 
	 * @param source
	 *            xml资源
	 * @return
	 */
	public List<HashMap<String, String>> getEntityList(String xmlstr) {
		try {
			StringReader sr = new StringReader(xmlstr);
			InputSource source = new InputSource(sr);
			this.datalist = getEntityList(source);
		} catch (Exception ex) {
			Logger.L.error("get datelist error:", ex);
		}
		return this.datalist;
	}

	/**
	 * 从xmlstr获取数据列表
	 * 
	 * @param context
	 *            当前上下文
	 * @param resid
	 *            xml string
	 * @return
	 */
	public HashMap<String, String> getListFromXmlStr(Context context,
													 String xmlstr) {
		try {
			StringReader sr = new StringReader(xmlstr);
			InputSource source = new InputSource(sr);
			this.KVList = getList(source);
		} catch (Exception ex) {
			Logger.L.error("get datalist error:", ex);
		}
		return this.KVList;
	}

	/**
	 * 从raw下获取数据列表
	 * 
	 * @param context
	 *            当前上下文
	 * @param resid
	 *            raw下的资源id
	 * @return
	 */
	public HashMap<String, String> getListFromRaw(Context context, int resid) {
		try {
			InputStream stream = context.getResources().openRawResource(resid);
			InputSource source = new InputSource(stream);
			this.KVList = getList(source);
		} catch (Exception ex) {
			Logger.L.error("get datalist error:", ex);
		}
		return this.KVList;
	}

	/**
	 * 从raw下获取数据列表
	 * 
	 * @param context
	 *            当前上下文
	 * @param resid
	 *            raw下的资源id
	 * @return
	 */
	public List<HashMap<String, String>> getEntityListFromRaw(Context context,
															  int resid) {
		try {
			InputStream stream = context.getResources().openRawResource(resid);
			InputSource source = new InputSource(stream);
			this.datalist = getEntityList(source);
		} catch (Exception ex) {
			Logger.L.error("get datalist error:", ex);
		}
		return this.datalist;
	}

	/**
	 * 从assets下获取数据列表
	 * 
	 * @param context
	 *            当前上下文
	 * @param resid
	 *            assets下的文件名(带后缀名)
	 * @return
	 */
	public HashMap<String, String> getListFromAssets(Context context,
													 String filename) {
		try {
			InputStream stream = context.getAssets().open(filename);
			InputSource source = new InputSource(stream);
			this.KVList = getList(source);
		} catch (Exception ex) {
			Logger.L.error("get datalist error:", ex);
		}
		return this.KVList;
	}

	/**
	 * 从assets下获取数据列表
	 * 
	 * @param context
	 *            当前上下文
	 * @param resid
	 *            assets下的文件名(带后缀名)
	 * @return
	 */
	public List<HashMap<String, String>> getEntityListFromAssets(
			Context context, String filename) {
		try {
			InputStream stream = context.getAssets().open(filename);
			InputSource source = new InputSource(stream);
			this.datalist = getEntityList(source);
		} catch (Exception ex) {
			Logger.L.error("get datalist error:", ex);
		}
		return this.datalist;
	}
}
