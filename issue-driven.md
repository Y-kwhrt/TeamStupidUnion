# issueを用いたTODO管理

## issueとは - What is "issue"
GitHubの便利機能。  
解決したい問題に関して議論するためのスレッドを立てることができる。

## 使い方 - Usage
1. [${repository}/[issues](https://github.com/Y-kwhrt/TeamStupidUnion/issues)] を開く
0. [[New issue](https://github.com/Y-kwhrt/TeamStupidUnion/issues/new/choose)] を選択
0. issueのタイトルを入力
0. [Submit new issue] で作成

## 用い方 - The way to make use
1. タスク(TODO)を言語化する  
  `登録機能を追加する` とか、  
  `Readme.mdの誤字を修正する` とか。  
0. **1.** をタイトルにした *issue* を作成  
  詳細説明を記入できる。  
  *issue*内で議論もできる。  
0. **2.** を解決する *Branch*`issue-#${id}` を作成  
  *issue*を作成するとIDが割り振られる。  
  IDはタイトルの横に書いてあるので確認する。  
  **ex**) `#10`  
  *Branch*名にIDを入れる。  
  **ex**) `issue-#10`  
  書きたいことがある場合、追記する。  
  **ex**) `issue-#10_Update_Readme`  
0. **2.** に連携した **3.** の *Pull Request* を作成  
  連携: *Pull Request* が解決したら *issue* も閉じられるようにすること。  
  close #${issueId} をコメントの適当な場所に入れておけば連携できる。  
  **ex**) close #10  
  issueへのリンクにもなるので便利。
0. **4.** を Merge [する/してもらう]

↓つまりこんな感じ
|||
|:--|---|
|*issue*|問題の提案|
|*Branch*|[問題(*issue*)]を解決するsource|
|*Pull Request*|[問題の解決(*Branch*)]の提案|
|*Merge*|[提案(*Pull Request*)]を受諾|
