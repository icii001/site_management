package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Query;

import forms.CsvSiteList;
import models.Site;
import play.*;
import play.data.Form;
import play.db.ebean.Model.Finder;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.*;

public class Application extends Controller {

	// 一覧画面表示メソッド
    public static Result index() {
    	Finder<Long, Site> finder =
    			new Finder<Long, Site>(Long.class, Site.class);
    	List<Site>siteList = finder.all();
    	String message ="";
        return ok(index.render(siteList,message));
    }

    // 登録画面表示メソッド
    public static Result register() {
    	Form<Site> siteForm = new Form<Site>(Site.class);
        return ok(register.render(siteForm));
    }

    // 登録画面表示メソッド(やりなおし)
    public static Result backRegister() {
    	Form<Site> site = new Form(Site.class).bindFromRequest();
        return ok(register.render(site));
    }

    // 登録確認画面表示メソッド
    public static Result send() {
    	Form<Site> site = new Form(Site.class).bindFromRequest();
    	if(site.hasErrors()) {
    		return ok(register.render(site));
    	}
    	Site data = site.get();
    	if(!site.hasErrors()) {
    		return ok(registConf.render(data));
    	}else {
    		return ok(error.render());
    	}
    }

    // 登録実行メソッド
    public static Result registConf() {
    	Form<Site> site = new Form(Site.class).bindFromRequest();
    	if(!site.hasErrors()) {
    		Site data = site.get();
    		data.save();
        	return redirect("/success?mt=rgst");
    	}else {
    		return ok(error.render());
    	}
    }

    public static Result success() {
    	Finder<Long, Site> finder =
    			new Finder<Long, Site>(Long.class, Site.class);
    	List<Site>siteList = finder.all();
    	String mt = "";
    	mt = request().getQueryString("mt");
    	String msg="";
    	String rgst="rgst";
    	String edit="edit";
    	String del="del";
    	if(mt.equals(rgst)) {
    		msg ="登録が完了しました！";
    	}else if(mt.equals(edit)) {
    		msg ="編集が完了しました！";
    	}else if(mt.equals(del)){
    		msg ="削除が完了しました！";
    	}
        return ok(index.render(siteList,msg));
    }

    // CSV登録画面表示メソッド
    public static Result csvRead() {
    	String msg = "";
    	return ok(csvRead.render(msg));
    }

    // CSV読込メソッド
    public static Result readCsv() {
    	  MultipartFormData body = request().body().asMultipartFormData();
    	  FilePart csvFile = body.getFile("csv");
    	  if (csvFile != null) {
//    	    String fileName = csvFile.getFilename();
//    	    String contentType = csvFile.getContentType();
    	    File f = csvFile.getFile();
    	    try {
    	    	BufferedReader br = new BufferedReader(new FileReader(f));
//    	    	Stream<String> file = br.lines();
    	    	String line;
    	    	String csv = "";
    	    	List<Site>csvList = new ArrayList<Site>();
    	    	// 1行ずつCSVファイルを読み込む
    	    	while ((line = br.readLine()) != null) {
    	    		System.out.println(line+"1");
    	    		csv = csv + line + ",";
//    	    		String[] data = line.split("/n", -1); // 行を改行区切りで配列に変換
//    	    		for (String elem : data) {
    	    		String[] record = line.split(",", -1);
    	    		Site site =  new Site(null, record[0], record[1], record[2], record[3]);
    	    		csvList.add(site);
//    	    		}
    	    	}
    	    	br.close();
    	    	return ok(csvReadConf.render(csvList,csv));
    	    } catch (IOException e) {
    	    	System.out.println(e);
    	    }
    	  }
    	  return ok(success.render("Error"));
    }

    // CSV登録実行メソッド
    public static Result csvRegist() {
    	Map<String, String[]> requestParam =
    	        request().body().asFormUrlEncoded();
    	String[] input = requestParam.get("csv");
    	String[] input2 = requestParam.get("csvLen");
    	String csv = input[0];
    	int len = Integer.parseInt(input2[0]);
    	System.out.println("0" + csv);
    	String n = "/n";
    	List <Site> csvList = new ArrayList<Site>();
    	String del= "";
    	for (int i = 0; i<len;i++) {
    		System.out.println("1" + csv);
    		String[] data = csv.split(n, 0); // 行を改行区切りで配列に変換
    		for (String elem : data) {
    			String[] record = csv.split(",", -1);
    			Site site =  new Site(null, record[0], record[1], record[2], record[3].replace(n, ""));
    			csvList.add(site);
    			del = record[0] +"," + record[1] +"," + record[2] +"," + record[3] +",";
    			System.out.println(csv);
    		}
    	csv = csv.replaceFirst(del, "");
    	System.out.println("sss" + csv);
    }

    int csvListSize = csvList.size();
    for(int i=0 ;i<csvListSize;i++) {
    	Site data = csvList.get(i);
    	data.save();
    }

    Finder<Long, Site> finder =
		new Finder<Long, Site>(Long.class, Site.class);
    List<Site>siteList = finder.all();
	return redirect("/success?mt=rgst");
}

    // 検索実行メソッド
    public static Result search() {
    	String message= "";
//    	id = Long.parseLong(request().getQueryString("id"));
    	String siteName = request().getQueryString("siteName");
    	String address = request().getQueryString("address");
    	String tel = request().getQueryString("tel");
    	String remarks = request().getQueryString("remarks");
//    	System.out.println(siteName+address+tel+remarks);
    	Finder<Long, Site> finder =
    			new Finder<Long, Site>(Long.class, Site.class);
    	List<Site>siteList = finder.where()
    			.ilike("siteName","%"+ siteName + "%")
    			.ilike("address","%"+ address + "%")
    			.ilike("tel","%"+ tel + "%")
    			.ilike("remarks","%"+ remarks + "%")
    			.findList();
    	return ok(index.render(siteList,message));
    }

    // 編集画面表示メソッド
    public static Result editor() {
    	Form<Site> siteForm = new Form(Site.class).bindFromRequest();
    	Finder<Long, Site> finder =
    			new Finder<Long, Site>(Long.class, Site.class);
    	Site data = siteForm.get();
    	Site site = finder.byId(data.id);
    	System.out.println(site.siteName);
        return ok(edit.render(siteForm,site));
    }

    // 編集確認画面表示メソッド
    public static Result editConf() {
    	Form<Site> siteForm = new Form(Site.class).bindFromRequest();
    	if(siteForm.hasErrors()) {
    		return ok(register.render(siteForm));
    	}
    	Site data = siteForm.get();
    		return ok(editConf.render(data));
    }

    // 編集実行メソッド
    public static Result edit() {
    	Form<Site> site = new Form(Site.class).bindFromRequest();
    	Site data = site.get();
    	data.update();
    	return redirect("/success?mt=edit");
    }

    // 編集画面表示メソッド(やりなおし)
    public static Result backEditConf() {
    	Map<String, String[]> requestParam =
    	        request().body().asFormUrlEncoded();
    	String[] input = requestParam.get("id");
    	Long id = Long.parseLong(input[0]);
    	Finder<Long, Site> finder =
    			new Finder<Long, Site>(Long.class, Site.class);
    	Site site = finder.byId(id);
    	Form<Site> siteForm = new Form(Site.class).bindFromRequest();
        return ok(edit.render(siteForm,site));
    }

    // 削除確認画面表示メソッド
    public static Result deleteConf() {
    	Long id = Long.parseLong(request().getQueryString("id"));
    	// idで検索してデータの取得、deleteConf画面に表示させる
    	Finder<Long, Site> finder =
    			new Finder<Long, Site>(Long.class, Site.class);
    	Site site = finder.byId(id);
    	return ok(delete.render(site));
    }

    // 削除実行メソッド
    public static Result delete() {
    	//deleteConfで取得したidのデータを削除する
    	Map<String, String[]> requestParam =
    	        request().body().asFormUrlEncoded();
    	String[] input = requestParam.get("id");
    	Long id = Long.parseLong(input[0]);
    	Finder<Long, Site> finder =
    			new Finder<Long, Site>(Long.class, Site.class);
    	Site delSite = finder.byId(id);
    	delSite.delete();
    	return redirect("/success?mt=del");
    }
}
