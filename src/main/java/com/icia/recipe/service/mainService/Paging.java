package com.icia.recipe.service.mainService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Paging {
	private int totalNum; 		// 전체 글의 개수
	private int pageNum; 		// 현재 페이지 번호
	private int listCount; //10		// 페이지당 나타낼 글의 갯수
	private int pageCount; //2		// 페이지그룹당 페이지 갯수  
	private String listUrl;
	// 게시판의 종류(url) //
	//private String boardName; 	// 게시판의 종류(url) //

	/*public Paging(int totalNum, int pageNum, int listCount, int pageCount, String listUrl) {
		this.totalNum = totalNum;
		this.pageNum = pageNum;
		this.listCount = listCount;
		this.pageCount = pageCount;
		this.listUrl = listUrl;
		//this.boardName = boardName;
	}*/

	@SuppressWarnings("unused")
	public String makeHtmlPaging() {
		// 전체 페이지 갯수   44    %  10
		int totalPage = (totalNum % listCount > 0)
				 //44/10 +1
				?totalNum/listCount+1 : totalNum/listCount;
		// 전체 페이지 그룹 갯수  5     %   2     [1][2]    [3][4]   [5]
		int totalGroup = (totalPage % pageCount > 0)
				? totalPage/pageCount+1 : totalPage/pageCount;
		// 현재 페이지가 속해 있는 그룹 번호   3p % 2
		int currentGroup = (pageNum % pageCount > 0)
				? pageNum/pageCount+1 : pageNum/pageCount;
		return makeHtml(currentGroup, totalPage, listUrl);
	}

	private String makeHtml(int currentGroup, int totalPage, String listUrl) {
		StringBuffer sb = new StringBuffer();
		//현재그룹의 시작 페이지 번호
		int start = (currentGroup * pageCount) 
				    - (pageCount - 1);
		//현재그룹의 끝 페이지 번호
		int end = (currentGroup * pageCount >= totalPage)
				? totalPage
				: currentGroup * pageCount;

		sb.append("<nav aria-label=\"Page navigation example\">")
				.append("<ul class=\"pagination justify-content-center\">")
				.append("<li class=\"page-item disabled\">");

		if (start != 1) {
			 sb.append("<a class=\"page-link\" href=\""+listUrl+ (start -1)+")"+"\" tabindex=\"-1\" aria-disabled=\"true\">이전</a>")
					.append("</li>");
		}
		for (int i = start; i <= end; i++) {
			/*if (pageNum != i) { //현재 페이지가 아닌 경우 링크처리
				sb.append("<a class='pno' href='"+listUrl+"pageNum=" + i + "'>");
				sb.append(" [ ");
				sb.append(i);
				sb.append(" ] ");
				sb.append("</a>");*/
				sb.append("<li class=\"page-item\"><a class=\"page-link\" href=\""+listUrl+ i +")" + "\">"+i+"</a></li>");
			/*} else { //현재 페이지인 경우 링크 해제
				sb.append("<font style='color:red;'>");
				sb.append(" [ ");
				sb.append(i);
				sb.append(" ] ");
				sb.append("</font>");
			}*/
		}
		if (end != totalPage) {
			/*sb.append("<a class='pno' href='"+listUrl+"pageNum=" + (end + 1) + "'>");
			sb.append("[다음]");
			sb.append("</a>");*/
/*			 sb.append("<li class=\"page-item\">")
					.append("<a class=\"page-link\" href=\""+listUrl+ (end -1)+")"+"\">Next</a>")
					.append("</li>")
					.append("</ul>")
					.append("</nav>");*/
		}
		return sb.toString();
	}
}
