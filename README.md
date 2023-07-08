# portfolio
【制作物について】 

◆目的:
Spring Boot及び PostgreSQLを使った勤怠管理アプリケーションを作りたい。 

◆目標: 
職業訓練で習得した技術と企業実習で行った課題で得た知識を使用し、登録ができる勤怠管理アプリケーションの作成。
 
◆概要:
登録・修正・表示ができる勤怠管理の Web アプリケーションです。 

◆機能:
案件の登録・修正・表示機能	 実装済
入力内容のバリデーション 	実装済


◆使用した環境等:
開発環境:Eclipse(Version: 2023-03 (4.27.0)) 
データベース:PostgreSQL(version 2.6.2 - 15) 
言語:Java(version 1.8.0) 

【プログラム構成】 
Model:主な処理とデータの格納を行う。 
View:画面の表示を行う。
Controller:処理の実行を Model に依頼、結果の表示を View に依頼する。 


*Model 
MsEmployee.java　エンティティクラス
AttendanceEntity.java	エンティティクラス

*View
empRef.html  社員情報照会画面
attRef.html　勤怠情報登録画面
attReg.html	　勤怠修正画面
index.html	ログイン画面の表示
menu.html	　メインメニュー画面の表示

*Controller
TestController.java コントローラクラス
SearchController.java　勤怠情報表示画面の制御
EntryController.java	検索画面遷移制御


【感想】
企業実習ではPostgreSQLを使用したが、訓練で使用したMySQLとはSQL文の文法が違う部分があり、インターネット検索で一つ一つ調べるのに苦労した。
タイムリーフを使ったHTMLの書き方も課題で使用したメモを見ながら手探りで行っていたが、最終的に理解が進み成長を感じられた。 
なかなか解決できなかった問題が解けたときは非常に達成感を得ることができ、やりがいを感じられた。 
まだまだ拙い部分は多いが今後も内容理解を深めるように努め、より高度なプログラミングをできるようになっていきたい。
