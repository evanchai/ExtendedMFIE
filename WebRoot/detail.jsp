<%@ page language="java" import="java.util.*,cn.edu.fudan.se.web.pojo.*, com.stackoverflow.bean.*"
  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!-- saved from url=(0090)http://stackoverflow.com/questions/27420164/paste-data-at-the-start-of-line-in-visual-mode -->
<html itemscope="" itemtype="http://schema.org/QAPage">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	    //Post post = (Post)(request.getAttribute("post"));
%>
<title>${post.post_title}- Stack Overflow</title>
<link rel="shortcut icon"
	href="http://cdn.sstatic.net/stackoverflow/img/favicon.ico?v=038622610830">
<link rel="apple-touch-icon image_src"
	href="http://cdn.sstatic.net/stackoverflow/img/apple-touch-icon.png?v=fd7230a85918">
<link rel="search" type="application/opensearchdescription+xml"
	title="Stack Overflow" href="http://stackoverflow.com/opensearch.xml">
<script type="text/javascript" src="./js/jquery-2.1.0.min.js"></script>

<meta name="twitter:card" content="summary">
<meta name="twitter:domain" content="stackoverflow.com">
<meta property="og:type" content="website">
<meta property="og:image" itemprop="image primaryImageOfPage"
	content="http://cdn.sstatic.net/stackoverflow/img/apple-touch-icon@2.png?v=fde65a5a78c6">
<meta name="twitter:title" property="og:title" itemprop="title name"
	content="paste data at the start of line in visual mode">
<meta name="twitter:description" property="og:description"
	itemprop="description"
	content="I can select lines using SHIFT + V, then selecting lines using up down left right keys, then copy them using y (yank them) and paste them using p (put).

I can similarly select data block using CTR...">
<meta property="og:url"
	content="http://stackoverflow.com/questions/27420164/paste-data-at-the-start-of-line-in-visual-mode">
<link rel="canonical"
	href="./vim - paste data at the start of line in visual mode - Stack Overflow_files/vim - paste data at the start of line in visual mode - Stack Overflow.html">

<script type="text/javascript" async=""
	src="./vim - paste data at the start of line in visual mode - Stack Overflow_files/ados"></script>
<script async=""
	src="./vim - paste data at the start of line in visual mode - Stack Overflow_files/quant.js"></script>
<script async=""
	src="./vim - paste data at the start of line in visual mode - Stack Overflow_files/beacon.js"></script>
<script async=""
	src="./vim - paste data at the start of line in visual mode - Stack Overflow_files/analytics.js"></script>
<script type="text/javascript" async=""
	src="./vim - paste data at the start of line in visual mode - Stack Overflow_files/ados.js"></script>
<script
	src="./vim - paste data at the start of line in visual mode - Stack Overflow_files/jquery.min.js"></script>
<script
	src="./vim - paste data at the start of line in visual mode - Stack Overflow_files/stub.en.js"></script>
<link rel="stylesheet" type="text/css"
	href="./vim - paste data at the start of line in visual mode - Stack Overflow_files/all.css">

<!-- <link rel="alternate" type="application/atom+xml"
	title="Feed for question &#39;paste data at the start of line in visual mode&#39;"
	href="http://stackoverflow.com/feeds/question/27420164"> -->
<script>

        StackExchange.ready(function () {
                
                StackExchange.using("snippets", function () {
                    StackExchange.snippets.initSnippetRenderer();
                });
                

            StackExchange.using("postValidation", function () {
                StackExchange.postValidation.initOnBlurAndSubmit($('#post-form'), 2, 'answer');
            });

            
            StackExchange.question.init({showAnswerHelp:true,totalCommentCount:2,shownCommentCount:2,highlightColor:'#F4A83D',backgroundColor:'#FFF',questionId:27420164});

            styleCode();

                StackExchange.realtime.subscribeToQuestion('1', '27420164');
            
                    });
    </script>


<script>
        StackExchange.init({"locale":"en","stackAuthUrl":"https://stackauth.com","serverTime":1418294581,"networkMetaHostname":"meta.stackexchange.com","routeName":"Questions/Show","styleCode":true,"enableUserHovercards":true,"snippets":{"enabled":true,"domain":"stacksnippets.net"},"site":{"name":"Stack Overflow","description":"Q&A for professional and enthusiast programmers","isNoticesTabEnabled":true,"recaptchaPublicKey":"6LdchgIAAAAAAJwGpIzRQSOFaO0pU6s44Xt8aTwc","recaptchaAudioLang":"en","enableNewTagCreationWarning":true,"nonAsciiTags":true,"enableSocialMediaInSharePopup":true},"user":{"fkey":"e984835d7eefa377ed4531644e13d706","isAnonymous":true}});
        StackExchange.using.setCacheBreakers({"js/prettify-full.en.js":"1d3cdccfa4a6","js/moderator.en.js":"226ceea94789","js/full-anon.en.js":"28b8bceed753","js/full.en.js":"35697576f428","js/wmd.en.js":"7f268c28ada2","js/third-party/jquery.autocomplete.min.js":"e5f01e97f7c3","js/third-party/jquery.autocomplete.min.en.js":"","js/mobile.en.js":"1871f23e40dd","js/help.en.js":"1f82ef88ce5b","js/tageditor.en.js":"c8d06452914a","js/tageditornew.en.js":"8a4e27e7b967","js/inline-tag-editing.en.js":"9d7c6b9d01c1","js/revisions.en.js":"989c474e85a9","js/review.en.js":"a9560a664f10","js/tagsuggestions.en.js":"d1ff9b84abe5","js/post-validation.en.js":"a5531b076cc2","js/explore-qlist.en.js":"257ba4cb7b04","js/events.en.js":"effe58c6a105","js/keyboard-shortcuts.en.js":"9b3d3d7b66fe","js/external-editor.en.js":"bc276cc6e2cd","js/external-editor.en.js":"bc276cc6e2cd","js/snippet-javascript.en.js":"4ddac7652103","js/snippet-javascript-codemirror.en.js":"9774b7b8d311"});
        StackExchange.using("gps", function() {
             StackExchange.gps.init(true);
        });
    </script>

<script>
            StackExchange.ready(function () {
                $('#nav-tour').click(function () {
                    StackExchange.using("gps", function() {
                        StackExchange.gps.track("aboutpage.click", { aboutclick_location: "headermain" }, true);
                    });
                });
            });
</script>
<%-- <%
	//跳入Servlet读取数据,为了防止一直跳转，所以用if来判断
	if (request.getAttribute("id") != null&&request.getAttribute("flash")==null) {
		//跳转，地址为web的那个名字 
		response.sendRedirect("post?id="+request.getAttribute("id"));
	}
%> --%>

<script type="text/javascript"
	src="./vim - paste data at the start of line in visual mode - Stack Overflow_files/adFeedback.js"></script>
<link rel="stylesheet"
	href="http://static.adzerk.net/Extensions/adFeedback.css">
</head>
<body class="question-page new-topbar">
	<!-- <div id="noscript-padding"></div>
 	<noscript>&lt;div id="noscript-padding"&gt;&lt;/div&gt;</noscript> -->
 	<div id="notify-container"></div>
	<div id="overlay-header"></div>
	<div id="custom-header"></div>

	<div class="topbar">
		<div class="topbar-wrapper">
			<div class="js-topbar-dialog-corral">
				<div class="topbar-dialog siteSwitcher-dialog dno">
					<div class="header">
						<h3>
							<a href="http://stackoverflow.com/">current community</a>
						</h3>
					</div>
					<div class="modal-content current-site-container">
						<ul class="current-site">
							<li>
								<div class="related-links">
									<a href="http://chat.stackoverflow.com/" class="js-gps-track"
										data-gps-track="site_switcher.click({ item_type:6 })">chat</a>
									<a href="http://blog.stackoverflow.com/" class="js-gps-track"
										data-gps-track="site_switcher.click({ item_type:7 })">blog</a>
								</div>
								 <a href="http://stackoverflow.com/"
								class="current-site-link site-link js-gps-track" data-id="1"
								data-gps-track="
        site_switcher.click({ item_type:3 })">
									<div class="site-icon favicon favicon-stackoverflow"
										title="Stack Overflow"></div> Stack Overflow
							</a>

							</li>
							<li class="related-site">
								<div class="L-shaped-icon-container">
									<span class="L-shaped-icon"></span>
								</div>
								 <a href="http://meta.stackoverflow.com/"
								class="site-link js-gps-track" data-id="552"
								data-gps-track="
            site.switch({ target_site:552, item_type:3 }),
        site_switcher.click({ item_type:4 })">
									<div class="site-icon favicon favicon-stackoverflowmeta"
										title="Meta Stack Overflow"></div> Meta Stack Overflow
							</a>

							</li>
							<li class="related-site">
								<div class="L-shaped-icon-container">
									<span class="L-shaped-icon"></span>
								</div>
							 <a class="site-link js-gps-track"
								href="http://careers.stackoverflow.com/?utm_source=stackoverflow.com&utm_medium=site-ui&utm_campaign=multicollider"
								data-gps-track="site_switcher.click({ item_type:9 })">
									<div class="site-icon favicon favicon-careers"
										title="Stack Overflow Careers"></div> Stack Overflow Careers
							</a>
							</li>
						</ul> 
					</div>

					<div class="header" id="your-communities-header">
						<h3>your communities</h3>

					</div>
					<div class="modal-content" id="your-communities-section">

						<div class="call-to-login">
							<a
								href="https://stackoverflow.com/users/signup?returnurl=http%3a%2f%2fstackoverflow.com%2fquestions%2f27420164%2fpaste-data-at-the-start-of-line-in-visual-mode"
								class="js-gps-track"
								data-gps-track="site_switcher.click({ item_type:10 })">Sign
								up</a> or <a
								href="https://stackoverflow.com/users/login?returnurl=http%3a%2f%2fstackoverflow.com%2fquestions%2f27420164%2fpaste-data-at-the-start-of-line-in-visual-mode"
								class="js-gps-track"
								data-gps-track="site_switcher.click({ item_type:11 })">log
								in</a> to customize your list.
						</div>
					</div>

					<div class="header">
						<h3>
							<a href="http://stackexchange.com/sites">more stack exchange
								communities</a>
						</h3>
					</div>
					<div class="modal-content">
						<div class="child-content"></div>
					</div>
				</div>
			</div>

			<div class="network-items">

				<a href="http://stackexchange.com/"
					class="topbar-icon icon-site-switcher yes-hover js-site-switcher-button js-gps-track"
					data-gps-track="site_switcher.show"
					title="A list of all 132 Stack Exchange sites"> <span
					class="hidden-text">Stack Exchange</span>
				</a>

			</div>

			<div class="topbar-links">

				<div class="links-container">
					<span class="topbar-menu-links"> <a
						href="https://stackoverflow.com/users/signup?returnurl=http%3a%2f%2fstackoverflow.com%2fquestions%2f27420164%2fpaste-data-at-the-start-of-line-in-visual-mode"
						class="login-link">sign up</a> <a
						href="https://stackoverflow.com/users/login?returnurl=http%3a%2f%2fstackoverflow.com%2fquestions%2f27420164%2fpaste-data-at-the-start-of-line-in-visual-mode"
						class="login-link">log in</a> <a
						href="http://stackoverflow.com/tour">tour</a> <a
						href="http://stackoverflow.com/questions/27420164/paste-data-at-the-start-of-line-in-visual-mode#"
						class="icon-help js-help-button"
						title="Help Center and other resources"> help <span
							class="triangle"></span>
					</a>
						<div class="topbar-dialog help-dialog js-help-dialog dno">
							<div class="modal-content">
								<ul>
									<li><a href="http://stackoverflow.com/tour"
										class="js-gps-track"
										data-gps-track="help_popup.click({ item_type:1 })"> Tour <span
											class="item-summary"> Start here for a quick overview
												of the site </span>
									</a></li>
									<li><a href="http://stackoverflow.com/help"
										class="js-gps-track"
										data-gps-track="help_popup.click({ item_type:4 })"> Help
											Center <span class="item-summary"> Detailed answers to
												any questions you might have </span>
									</a></li>
									<li><a href="http://meta.stackoverflow.com/"
										class="js-gps-track"
										data-gps-track="help_popup.click({ item_type:2 })"> Meta <span
											class="item-summary"> Discuss the workings and
												policies of this site </span>
									</a></li>
								</ul>
							</div>
						</div> <a
						href="http://careers.stackoverflow.com/?utm_source=stackoverflow.com&utm_medium=site-ui&utm_campaign=anon-topbar">stack
							overflow careers</a>
					</span>
				</div>

				<div class="search-container">
					<form id="search" action="http://stackoverflow.com/search"
						method="get" autocomplete="off">
						<input name="q" type="text" placeholder="search" value=""
							tabindex="1" autocomplete="off" maxlength="240">
					</form>
				</div>

			</div>
		</div>
	</div>
	<script>
        StackExchange.ready(function() { StackExchange.topbar.init(); });
    </script>

	<div class="container">
		<div id="header">
			<br class="cbt">
			<div id="hlogo">
				<a href="http://stackoverflow.com/"> Stack Overflow </a>
			</div>
			<div id="hmenus">
				<div class="nav mainnavs">
					<ul>
						<li class="youarehere"><a id="nav-questions"
							href="http://stackoverflow.com/questions">Questions</a></li>
						<li><a id="nav-tags" href="http://stackoverflow.com/tags">Tags</a></li>
						<li><a id="nav-users" href="http://stackoverflow.com/users">Users</a></li>
						<li><a id="nav-badges"
							href="http://stackoverflow.com/help/badges">Badges</a></li>
						<li><a id="nav-unanswered"
							href="http://stackoverflow.com/unanswered">Unanswered</a></li>
					</ul>
				</div>
				<div class="nav askquestion">
					<ul>
						<li><a id="nav-askquestion"
							href="http://stackoverflow.com/questions/ask">Ask Question</a></li>
					</ul>
				</div>
			</div>
		</div>




		<div id="content" class="snippet-hidden">


			<div itemscope="" itemtype="http://schema.org/Question">
				<link itemprop="image"
					href="http://cdn.sstatic.net/stackoverflow/img/apple-touch-icon.png">
				<!--googleoff: all-->

				<div id="herobox-mini">
					<div id="hero-content">
						<span id="controls"> <a
							href="http://stackoverflow.com/tour" id="tell-me-more"
							class="button">Take the 2-minute tour</a> <span id="close"><a
								title="click to dismiss">×</a></span>
						</span>
						<div id="blurb">Stack Overflow is a question and answer site
							for professional and enthusiast programmers. It's 100% free, no
							registration required.</div>
					</div>
					<script>
        $('#tell-me-more').click(function () {
            var clickSource = $("body").attr("class") + '-mini';
            if ($("body").hasClass("questions-page")) {
                clickSource = 'questionpagemini';
            } else if ($("body").hasClass("question-page")) {
                clickSource = 'questionpagemini';
            } else if ($("body").hasClass("home-page")) {
                clickSource = 'homepagemini';
            }

            StackExchange.using("gps", function () {
                StackExchange.gps.track("aboutpage.click", { aboutclick_location: clickSource } , true);
            });
        });
        $('#herobox-mini #close').click(function () {
            StackExchange.using("gps", function () {
                StackExchange.gps.track("hero.action", { hero_action_type: "close" }, true);
            });
            $.cookie("hero", "none", { path: "/", expires: 365 });
            var $hero = $("#herobox-mini");
            $hero.slideUp('fast', function () { $hero.remove(); });
            return false;
        });
    </script>
				</div>
				<!--googleon: all-->
				<div id="question-header">
					<h1 itemprop="name">
						<a
							href="./vim - paste data at the start of line in visual mode - Stack Overflow_files/vim - paste data at the start of line in visual mode - Stack Overflow.html"
							class="question-hyperlink">${post.post_title}</a>
					</h1>
				</div>
				<div id="mainbar">



					<div class="question" data-questionid="27420164" id="question">

			<script>
                var ados = ados || {};ados.run = ados.run || [];
                ados.run.push(function() { ados_add_placement(22,8277,"adzerk26551579",4).setZone(43) ; });                    
            </script>
						<!-- this is an advertise
						 <div class="everyonelovesstackoverflow" id="adzerk26551579">
							<a
								href="http://engine.adzerk.net/r?e=eyJhdiI6NDE0LCJhdCI6NCwiYnQiOjAsImNtIjoxODY2MjQsImNoIjoxMTc4LCJjciI6Njg0MjgwLCJkaSI6IjZlZmI0ZjkxZDE2NDRiMjA4YzdmYzMwZDU1ZmM1NGYwIiwiZG0iOjEsImZjIjo3MDg0MzQsImZsIjo0MzE3ODAsImlwIjoiMjAyLjEyMC4yMjQuNTMiLCJrdyI6InZpbSx2aSIsIm53IjoyMiwicGMiOjAsInByIjoxNjA0LCJydCI6MSwicmYiOiJodHRwOi8vc3RhY2tvdmVyZmxvdy5jb20vIiwic3QiOjgyNzcsInVrIjoidWUxLWVmNDM5ZTExN2VjMTQ0OWJiNDY4ZDQ5MjM0MzkyYmE3Iiwiem4iOjQzLCJ0cyI6MTQxODI5NDYzMTg0NCwiYmYiOnRydWUsInVyIjoiaHR0cDovL2NhcmVlcnMuc3RhY2tvdmVyZmxvdy5jb20vcHJvZHVjdHM_dXRtX3NvdXJjZT1zdGFja292ZXJmbG93LmNvbSZ1dG1fbWVkaXVtPWFkJnV0bV9jYW1wYWlnbj1lbXBsb3llcnMtYmVzdC13YXkmdXRtX2NvbnRlbnQ9bGItYmVzdC13YXkifQ&s=fFgCOb0IXuTkWHGm-OuQxzxSCKk"
								rel="nofollow" target="_blank" title=""><img
								src="./vim - paste data at the start of line in visual mode - Stack Overflow_files/6a84d696ad6c4679804e4923a617ade4.png"
								title="" alt="" border="0" width="728" height="90"></a><img
								height="0px" width="0px" border="0" style="position: absolute;"
								src="./vim - paste data at the start of line in visual mode - Stack Overflow_files/i.gif">
						</div> -->
						<table>
							<tbody>
								<tr>
									<td class="votecell">


										<div class="vote">
											<input type="hidden" name="_id_" value="27420164"> <a
												class="vote-up-off"
												title="This question shows research effort; it is useful and clear">up
												vote</a> <span itemprop="upvoteCount" class="vote-count-post ">3<%-- ${post.score} --%></span>
											<a class="vote-down-off"
												title="This question does not show any research effort; it is unclear or not useful">down
												vote</a> <a class="star-off"
												href="http://stackoverflow.com/questions/27420164/paste-data-at-the-start-of-line-in-visual-mode#"
												title="This is a favorite question (click again to undo)">favorite</a>
											<div class="favoritecount">
												<b></b>
											</div>

										</div>

									</td>

									<td class="postcell">
										<div>
											<div class="post-text" itemprop="text">${post.post_body}</div>
											<div class="post-taglist">
		
											<c:forEach items="${post.post_tag}" var="t">
											<a href="http://stackoverflow.com/questions/tagged/vim"
													class="post-tag"
													title="show questions tagged &#39;vim&#39;" rel="tag">${t}</a>
												 
											</c:forEach>	
											</div>
											<table class="fw">
												<tbody>
													<tr>
														<td class="vt">
															<div class="post-menu">
																<a href="http://stackoverflow.com/q/27420164"
																	title="short permalink to this question"
																	class="short-link" id="link-post-27420164">share</a><span
																	class="lsep">|</span><a
																	href="http://stackoverflow.com/posts/27420164/edit"
																	class="suggest-edit-post" title="">improve this
																	question</a>
															</div>
														</td>
														<td align="right" class="post-signature">
															<div class="user-info ">
																<div class="user-action-time">
																	<a
																		href="http://stackoverflow.com/posts/27420164/revisions"
																		title="show all edits to this post">edited <span
																		title="2014-12-11 10:40:43Z" class="relativetime">2013-06-19<%-- ${post.lastEditDate } --%></span></a>
																</div>
																<div class="user-gravatar32"></div>
																<div class="user-details">
																	<br>

																</div>
															</div>
														</td>
														<td class="post-signature owner">
															<div class="user-info user-hover">
																<div class="user-action-time">
																	asked <span title="2014-12-11 10:10:04Z"
																		class="relativetime">2013-07-17$<%-- {post.writeDate } --%></span>
																</div>
																<div class="user-gravatar32">
																	<a
																		href="http://stackoverflow.com/users/1860929/mu-%e7%84%a1"><div
																			class="gravatar-wrapper-32">
																			<img
																				src="./vim - paste data at the start of line in visual mode - Stack Overflow_files/eDsrN.jpg"
																				alt="" width="32" height="32">
																		</div></a>
																</div>
																<div class="user-details">
																	<a
																		href="http://stackoverflow.com/users/1860929/mu-%e7%84%a1">8 null$<%-- {post.ownerUserId}${post.ownerDisplayName } --%></a><br>
																	<span class="reputation-score"
																		title="reputation score 10258" dir="ltr">10.3k</span><span
																		title="8 gold badges"><span class="badge1"></span><span
																		class="badgecount">8</span></span><span
																		title="21 silver badges"><span class="badge2"></span><span
																		class="badgecount">21</span></span><span
																		title="43 bronze badges"><span class="badge3"></span><span
																		class="badgecount">43</span></span>
																</div>
															</div>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</td>
								</tr>
 
							<tr>
									<td class="votecell"></td>
									<td>
										<div id="comments-27420164" class="comments ">
											<table>
												<tbody data-remaining-comments-count="0"
													data-canpost="false" data-cansee="true"
													data-comments-unavailable="false"
													data-addlink-disabled="true">
													<c:forEach items="${post.commentList }" var="c">
														<tr id="comment-43283186" class="comment ">
															<td>
																<table>
																	<tbody>
																		<tr>
																			<td class=" comment-score">&nbsp;<span title="number of 'useful comment' votes received" class = "cool">2<%-- ${c.score } --%></span>&nbsp;</td>
																			<td>&nbsp;</td>
																		</tr>
																	</tbody>
																</table>
															</td>
															<td class="comment-text">
																<div style="display: block;" class="comment-body">
																	<span class="comment-copy">${c.comment_text}</span>; 
																	
<%-- 																	 <c:if test="${c.userId == post.ownerUserId}">
																			<a href="/users/338/frank-krueger"
																				title="29295 reputation" class="comment-user owner">
																			${c.userId }
																				${c.userDisplayName }
																			</a> 
																	 </c:if>
																	 <c:if test="${c.userId != post.ownerUserId}">
																			<a href="/users/338/frank-krueger"
																				title="29295 reputation" class="comment-user">
																			${c.userId }
																				${c.userDisplayName }
																			</a> 	
																	</c:if> --%>
																		 <span class="comment-date"
																		dir="ltr"><span title="2014-12-11 10:13:07Z"
																		class="relativetime-clean">2014-12-11<%-- ${c.creationDate } --%></span></span>
																</div>
															</td>

														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
										<div id="comments-link-27420164" data-rep="50"
											data-anon="true">

											<a class="js-add-link comments-link disabled-link "
												title="Use comments to ask for more information or suggest improvements. Avoid answering questions in comments.">add
												a comment</a><span class="js-link-separator dno">&nbsp;|&nbsp;</span><a
												class="js-show-link comments-link dno"
												title="expand to show all comments on this post, or add one of your own"
												href="http://stackoverflow.com/questions/27420164/paste-data-at-the-start-of-line-in-visual-mode#"
												onClick=""></a>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div id="answers">

						<a name="tab-top"></a>
						<div id="answers-header">
							<div class="subheader answers-subheader">
								<h2>
									${post.post_answer_count} Answers 
									<span style="display: none;"
										itemprop="answerCount">${post.post_answer_count}</span>
								</h2>
								<div>
									<div id="tabs">
										<a
											href="http://stackoverflow.com/questions/27420164/paste-data-at-the-start-of-line-in-visual-mode?answertab=active#tab-top"
											title="Answers with the latest activity first">active</a> <a
											href="http://stackoverflow.com/questions/27420164/paste-data-at-the-start-of-line-in-visual-mode?answertab=oldest#tab-top"
											title="Answers in the order they were provided">oldest</a> <a
											class="youarehere"
											href="http://stackoverflow.com/questions/27420164/paste-data-at-the-start-of-line-in-visual-mode?answertab=votes#tab-top"
											title="Answers with the highest score first">votes</a>
									</div>
								</div>
							</div>
						</div>
						
						<c:forEach items="${post.answerList}" var="m">
							<a name="${m.postId}"></a>
							<div id="answer" +"${m.postId}" class="answer accepted-answer"
								data-answerid="${m.postId}" itemscope=""
								itemtype="http://schema.org/Answer" itemprop="acceptedAnswer">
								<table>
									<tbody>
										<tr>
											<td class="votecell">
												<div class="vote">
													<input type="hidden" name="_id_" value="${m.postId}">
													<a class="vote-up-off" title="This answer is useful">up
														vote</a> <span itemprop="upvoteCount" class="vote-count-post ">3<%-- ${m.score} --%></span>
													<a class="vote-down-off" title="This answer is not useful">down
														vote</a> 
													<span
														class="vote-accepted-on load-accepted-answer-date"
														id="accepted"
														title="loading when this answer was accepted..." >
														<%-- ${m.accepted} --%>
													</span>
													<!--  <div style="color:red">${m.accepted}</div>-->
												</div>
											</td>
											<td class="answercell">
												<div class="post-text" itemprop="text">${m.post_body}</div>
												<table class="fw">
													<tbody>
														<tr>
															<td class="vt">
																<div class="post-menu">
																	<a href="http://stackoverflow.com/a/27420708"
																		title="short permalink to this answer"
																		class="short-link" id="link-post-27420708">share</a>
																</div>
															</td>



															<td align="right" class="post-signature">


																<div class="user-info user-hover">
																	<div class="user-action-time">
																		answered <span title="2014-12-11 10:33:50Z"
																			class="relativetime">2014-12-11<%-- ${m.writeDate } --%></span>
																	</div>
																	<div class="user-gravatar32">
																		<a href="http://stackoverflow.com/users/164835/kent"><div
																				class="gravatar-wrapper-32">
																				<img
																					src="http://i.stack.imgur.com/ayYoh.jpg?s=32&g=1"
																					alt="" width="32" height="32">
																			</div></a>
																	</div>
																	<div class="user-details">
																		<a href="http://stackoverflow.com/users/164835/kent"><%-- ${m.ownerUserId }
																			${m.ownerDisplayName } --%></a><br> <span
																			class="reputation-score"
																			title="reputation score 78162" dir="ltr">78.2k</span><span
																			title="9 gold badges"><span class="badge1"></span><span
																			class="badgecount"></span></span><span
																			title="52 silver badges"><span class="badge2"></span><span
																			class="badgecount"></span></span><span
																			title="98 bronze badges"><span class="badge3"></span><span
																			class="badgecount"></span></span>
																	</div>
																</div>
															</td>
														</tr>
													</tbody>
												</table>
											</td>
										</tr>

										<tr>
											<td class="votecell"></td>

											<td>
												<div id="comments-575811" class="comments ">
													<table>
														<tbody data-remaining-comments-count="0"
															data-canpost="false" data-cansee="true"
															data-comments-unavailable="false"
															data-addlink-disabled="true">


															<c:forEach items="${m.commentList}" var="mc">
															<%
															System.out.println("answercomment:");
															 %>
																<tr id="comment-387639" class="comment">
																	<td>
																		<table>
																			<tbody>
																				<tr>
																					<td class=" comment-score">&nbsp;<span title="number of 'useful comment' votes received" class = "cool">2<%-- ${mc.score } --%></span>&nbsp;</td>
																					<td>&nbsp;</td>
																				</tr>
																			</tbody>
																		</table>
																	</td>
																	<td class="comment-text">
																		<div style="display: block;" class="comment-body">
																			<span class="comment-copy">${mc.comment_text}</span>
																			&ndash;&nbsp; 
																			<%--  <c:if test="${mc.userId == post.ownerUserId}">
																			<a href="/users/338/frank-krueger"
																				title="29295 reputation" class="comment-user owner">
																			${mc.userId }
																				${mc.userDisplayName }
																			</a> 
																			</c:if>
																			<c:if test="${mc.userId != post.ownerUserId}">
																			<a href="/users/338/frank-krueger"
																				title="29295 reputation" class="comment-user">
																			${mc.userId }
																				${mc.userDisplayName }
																			</a> 	
																			</c:if> --%>
																				
																																				
																			<span class="comment-date"
																				dir="ltr"><span title="2009-02-22 21:22:07Z"
																				class="relativetime-clean">2009-02-22<%-- ${mc.creationDate } --%></span></span>
																		</div>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>

												<div id="comments-link-575811" data-rep=50 data-anon=true>

													<a class="js-add-link comments-link disabled-link "
														title="Use comments to ask for more information or suggest improvements. Avoid comments like “+1” or “thanks”.">add
														a comment</a><span class="js-link-separator dno">&nbsp;|&nbsp;</span><a
														class="js-show-link comments-link dno"
														title="expand to show all comments on this post, or add one of your own"
														href=# onClick=""></a>
												</div>
											</td>
										</tr>
								</table>
							</div>

						</c:forEach>

						<script>
                var ados = ados || {};ados.run = ados.run || [];
                ados.run.push(function() { ados_add_placement(22,8277,"adzerk28525039",4).setZone(44) ; });                    
            </script>
<!-- <script type="text/javascript">

initPic();
function initPic() {
	var accepted = document.getElementById("accepted");
//	alert(accepted.innerText);
	if(accepted.innerText == "true"){
		src="./vim - paste data at the start of line in visual mode - Stack Overflow_files/duihao.jpg";
	//	accepted.style.backgroundImage="url("+src+")";
		accepted.style.backgroundColor="green";
			
	}
		
}


</script> -->

						<div class="everyonelovesstackoverflow" id="adzerk28525039">
						</div>

						</ul>
						<a name="new-answer"></a>
						<form id="post-form"
							action="http://stackoverflow.com/questions/27420164/answer/submit"
							method="post" class="post-form">
							<input type="hidden" id="post-id" value="27420164"> <input
								type="hidden" id="qualityBanWarningShown"
								name="qualityBanWarningShown" value="false"> <input
								type="hidden" name="referrer" value="http://stackoverflow.com/">
							<h2 class="space">Your Answer</h2>



							<script>
            StackExchange.ifUsing("editor", function () {
                StackExchange.using("externalEditor", function () {
                    StackExchange.using("snippets", function () {
                        StackExchange.snippets.init();
                    });
                });
            }, "code-snippets");
        </script>

							<script>
							

							
													
    StackExchange.ready(function() {
        initTagRenderer("".split(" "), "".split(" "));
       
        StackExchange.using("externalEditor", function() {
            // Have to fire editor after snippets, if snippets enabled
            if (StackExchange.options.snippets.enabled) {
                StackExchange.using("snippets", function() {
                    createEditor();
                });
            }
            else {
                createEditor();
            }
        });

        function createEditor() {
            prepareEditor({
                heartbeatType: 'answer',
                bindNavPrevention: true,
                postfix: "",
                    onDemand: true,
                    discardSelector: ".discard-answer"
                ,immediatelyShowMarkdownHelp:true
                });
                

        }
    });  
</script>


							<div id="post-editor" class="post-editor">

								<div style="position: relative;">
									<div class="wmd-container">
										<div id="wmd-button-bar" class="wmd-button-bar"></div>
										<textarea id="wmd-input" class="wmd-input" name="post-text"
											cols="92" rows="15" tabindex="101" data-min-length=""></textarea>
									</div>
								</div>

								<div class="fl" style="margin-top: 8px; height: 24px;">&nbsp;</div>
								<div id="draft-saved" class="draft-saved community-option fl"
									style="margin-top: 8px; height: 24px; display: none;">draft
									saved</div>

								<div id="draft-discarded"
									class="draft-discarded community-option fl"
									style="margin-top: 8px; height: 24px; display: none;">draft
									discarded</div>



								<div id="wmd-preview" class="wmd-preview"></div>
								<div></div>
								<div class="edit-block">
									<input id="fkey" name="fkey" type="hidden"
										value="e984835d7eefa377ed4531644e13d706"> <input
										id="author" name="author" type="text">
								</div>
							</div>
							<div style="position: relative;">

								<div class="form-item dno new-post-login">

									<div class="new-login-form">
										<div class="new-login-left">
											<h3>
												Sign up or <a id="login-link"
													href="http://stackoverflow.com/users/login?returnurl=%2fquestions%2f27420164%2fpaste-data-at-the-start-of-line-in-visual-mode%23new-answer">log
													in</a>
											</h3>
											<script>
                            StackExchange.ready(function () {
                                StackExchange.helpers.onClickDraftSave('#login-link');
                            });
                        </script>
											<div class="preferred-login google-login">
												<p>
													<span class="icon"></span><span>Sign up using Google</span>
												</p>
											</div>
											<div class="preferred-login facebook-login">
												<p>
													<span class="icon"></span><span>Sign up using
														Facebook</span>
												</p>
											</div>
											<div class="preferred-login stackexchange-login">
												<p>
													<span class="icon"></span><span>Sign up using Stack
														Exchange</span>
												</p>
											</div>
										</div>
										<input type="hidden" name="manual-openid"
											class="manual-openid"> <input type="hidden"
											name="use-facebook" class="use-facebook" value="false">
										<input type="hidden" name="use-google" class="use-google"
											value="false"> <input type="button"
											class="submit-openid" value="Submit" style="display: none">
										<div class="new-login-right">
											<h3>Post as a guest</h3>
											<div class="form-item">
												<table>
													<tbody>
														<tr>
															<script>
                StackExchange.ready(function () {
                    StackExchange.helpers.bindHelpOverlayEvents($('.vm input'));
                });
            </script>
															<td class="vm">
																<div>
																	<label for="display-name">Name</label> <input
																		id="display-name" name="display-name" type="text"
																		size="30" maxlength="30" value="" tabindex="105">
																</div>
																<div>
																	<label for="m-address">Email</label> <input
																		id="m-address" name="m-address" type="text" size="30"
																		maxlength="100" value="" tabindex="106"> <span
																		class="edit-field-overlay">required, but not
																		shown</span>
																</div>
															</td>
														</tr>
													</tbody>
												</table>
											</div>

										</div>
									</div>
								</div>
								<script>
                StackExchange.ready(
                    function () {
                        StackExchange.openid.initPostLogin('.new-post-login', '%2fquestions%2f27420164%2fpaste-data-at-the-start-of-line-in-visual-mode%23new-answer');
                    }
                );
            </script>
								<noscript>&lt;h3&gt;Post as a guest&lt;/h3&gt;
									&lt;div class="form-item"&gt; &lt;table&gt; &lt;tr&gt;
									&lt;script&gt; StackExchange.ready(function () {
									StackExchange.helpers.bindHelpOverlayEvents($('.vm input'));
									}); &lt;/script&gt; &lt;td class="vm"&gt; &lt;div&gt; &lt;label
									for="display-name"&gt;Name&lt;/label&gt; &lt;input
									id="display-name" name="display-name" type="text" size="30"
									maxlength="30" value="" tabindex="105"&gt; &lt;/div&gt;
									&lt;div&gt; &lt;label for="m-address"&gt;Email&lt;/label&gt;
									&lt;input id="m-address" name="m-address" type="text" size="30"
									maxlength="100" value="" tabindex="106"&gt; &lt;span
									class="edit-field-overlay"&gt;required, but not
									shown&lt;/span&gt; &lt;/div&gt; &lt;/td&gt; &lt;/tr&gt;
									&lt;/table&gt; &lt;/div&gt;</noscript>
							</div>

							<div class="form-submit cbt">
								<input id="submit-button" type="submit" value="Post Your Answer"
									tabindex="110"> <a
									href="http://stackoverflow.com/questions/27420164/paste-data-at-the-start-of-line-in-visual-mode#"
									class="discard-answer dno">discard</a>

								<p class="privacy-policy-agreement">
									By posting your answer, you agree to the <a
										href="http://stackexchange.com/legal/privacy-policy"
										target="_blank">privacy policy</a> and <a
										href="http://stackexchange.com/legal/terms-of-service"
										target="_blank">terms of service</a>.
								</p>
								<input type="hidden" name="legalLinksShown" value="1">
							</div>
						</form>



						<h2 class="bottom-notice" data-loc="1">
							Not the answer you're looking for? Browse other questions tagged
						<c:forEach items="${post.post_tag}" var="t">
							
							<a href="http://stackoverflow.com/questions/tagged/vim"								
								class="post-tag" title="show questions tagged &#39;vim&#39;"
								rel="tag">${t}</a> 
							</c:forEach>	
								 or <a
								href="http://stackoverflow.com/questions/ask">ask your own
								question</a>.
						</h2>
					</div>
				</div>
				<div id="sidebar" class="show-votes">
					<div class="module question-stats">
						<table id="qinfo">
							<tbody>
								<tr>
									<td>
										<p class="label-key">asked</p>
									</td>
									<td style="padding-left: 10px">
										<p class="label-key" title="2014-12-11 10:10:04Z">
											<b>today</b>
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p class="label-key">viewed</p>
									</td>

									<td style="padding-left: 10px">
										<p class="label-key">
											<b>11 times</b>
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p class="label-key">active</p>
									</td>
									<td style="padding-left: 10px">
										<p class="label-key">
											<b><a
												href="http://stackoverflow.com/questions/27420164/paste-data-at-the-start-of-line-in-visual-mode?lastactivity"
												class="lastactivity-link" title="2014-12-11 10:40:43Z">today</a></b>
										</p>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<script>
                var ados = ados || {};ados.run = ados.run || [];
                ados.run.push(function() { ados_add_placement(22,8277,"adzerk1533122300",[17,2221]).setZone(45) ; });                    
            </script>
					<div class="everyonelovesstackoverflow" id="adzerk1533122300">
						<div id="SE20-ad-container" style="height: 250px; width: 220px;"></div>
						<script type="text/javascript">window._launchedAdUrl= "http://engine.adzerk.net/r?e=eyJhdiI6NDE0LCJhdCI6MTcsImNtIjo5OTMsImNoIjoxMTc4LCJjciI6MzE5NCwiZGkiOiJmNzkxYTY2MDkwMjc0NzBhOGVlMTcwYzc5NWNjMGQwOSIsImRtIjoxLCJmYyI6MTE3MTksImZsIjoyODQxLCJpcCI6IjIwMi4xMjAuMjI0LjUzIiwia3ciOiJ2aW0sdmkiLCJudyI6MjIsInBjIjowLCJwciI6MTYwNCwicnQiOjEsInJmIjoiaHR0cDovL3N0YWNrb3ZlcmZsb3cuY29tLyIsInN0Ijo4Mjc3LCJ1ayI6InVlMS1lZjQzOWUxMTdlYzE0NDliYjQ2OGQ0OTIzNDM5MmJhNyIsInpuIjo0NSwidHMiOjE0MTgyOTQ2MzE4NDYsImJmIjp0cnVlLCJ1ciI6bnVsbH0&s=Iur71A2kgNrK74GrKLfv3f3voi8";</script>
						<script type="text/javascript"
							src="./vim - paste data at the start of line in visual mode - Stack Overflow_files/js"></script>
						<img height="0px" width="0px" border="0"
							style="position: absolute;"
							src="./vim - paste data at the start of line in visual mode - Stack Overflow_files/i(1).gif">
					</div>
					<div id="hireme">
						<script>
;(function(d,g){var h=document;var a=window.$;var n=100;var c;var o="#sidebar [id^='adzerk'].everyonelovesstackoverflow";var k="#adzerk1";var b="div#hireme, div.hireme";function i(q){return a(q).html().replace(/\s+/g,"").length>0}function p(r){var q=a(o);if(q.length===0){return true}return !i(q)}function l(){var q=a(b);if(q.length>0){var r=a.map(q,function(s){return"d="+s.id});var t=h.createElement("script");t.type="text/javascript";t.src=d+(d.indexOf("?")===-1?"?":"&")+r.join("&");if(f()){t.src+="&l=1"}if(location.hash){t.src+="&"+location.hash.replace(/^#/,"")}if(g){t.src+="&background=true"}if(StackExchange&&StackExchange.options&&StackExchange.options.user&&StackExchange.options.user.accountId){t.src+="&ac="+StackExchange.options.user.accountId}h.body.appendChild(t)}}function f(){return a("#careersadsdoublehigh").length>0}function e(){return a("#commdoubhi").length>0}function m(){clearTimeout(c);if(p(k)||p(o)){c=setTimeout(m,n);return}var q=location.hash.indexOf("#abort")!==-1||e();if(q){j();return}l()}function j(){clearTimeout(c);if(g){return}var q=a(b);q.each(function(){var r=a(this);if(!i(r)){var t=r;var s=r.parents(".everyonelovesstackoverflow").first();if(s.length>0){t=s}t.remove()}})}setTimeout(j,2000);c=setTimeout(m,n)}).apply(null, ["//clc.stackoverflow.com/j/p",false]);            </script>

					</div>

<!--  
					<div class="module sidebar-related">
						<h4 id="h-related">Related</h4>
						<div class="related js-gps-related-questions" data-tracker="rq=1">
							<div class="spacer">
								<a href="http://stackoverflow.com/q/235839"
									title="Vote score (upvotes - downvotes)">
									<div class="answer-votes answered-accepted extra-large">1112
									</div>
								</a><a
									href="http://stackoverflow.com/questions/235839/indent-multiple-lines-quickly-in-vi"
									class="question-hyperlink">Indent multiple lines quickly in
									vi</a>
							</div>
							<div class="spacer">
								<a href="http://stackoverflow.com/q/741814"
									title="Vote score (upvotes - downvotes)">
									<div class="answer-votes answered-accepted large">112</div>
								</a><a
									href="http://stackoverflow.com/questions/741814/move-entire-line-up-and-down-in-vim"
									class="question-hyperlink">Move entire line up and down in
									Vim</a>
							</div>
							<div class="spacer">
								<a href="http://stackoverflow.com/q/1218390"
									title="Vote score (upvotes - downvotes)">
									<div class="answer-votes answered-accepted extra-large">1131
									</div>
								</a><a
									href="http://stackoverflow.com/questions/1218390/what-is-your-most-productive-shortcut-with-vim"
									class="question-hyperlink">What is your most productive
									shortcut with Vim?</a>
							</div>
							<div class="spacer">
								<a href="http://stackoverflow.com/q/4848254"
									title="Vote score (upvotes - downvotes)">
									<div class="answer-votes answered-accepted default">4</div>
								</a><a
									href="http://stackoverflow.com/questions/4848254/vim-search-pattern-for-a-piece-of-text-line-yanked-in-visual-mode"
									class="question-hyperlink">vim search pattern for a piece
									of text line yanked in visual mode</a>
							</div>
							<div class="spacer">
								<a href="http://stackoverflow.com/q/5379837"
									title="Vote score (upvotes - downvotes)">
									<div class="answer-votes default">3</div>
								</a><a
									href="http://stackoverflow.com/questions/5379837/is-it-possible-to-mapping-alt-hjkl-in-insert-mode"
									class="question-hyperlink">is it possible to mapping
									Alt-hjkl in Insert mode?</a>
							</div>
							<div class="spacer">
								<a href="http://stackoverflow.com/q/8906905"
									title="Vote score (upvotes - downvotes)">
									<div class="answer-votes answered-accepted default">12</div>
								</a><a
									href="http://stackoverflow.com/questions/8906905/how-to-yank-from-the-command-line"
									class="question-hyperlink">How to yank from the command
									line</a>
							</div>
							<div class="spacer">
								<a href="http://stackoverflow.com/q/14460083"
									title="Vote score (upvotes - downvotes)">
									<div class="answer-votes answered-accepted default">4</div>
								</a><a
									href="http://stackoverflow.com/questions/14460083/map-paste-p-to-0p"
									class="question-hyperlink">map paste (p) to "0p</a>
							</div>
							<div class="spacer">
								<a href="http://stackoverflow.com/q/16942485"
									title="Vote score (upvotes - downvotes)">
									<div class="answer-votes default">7</div>
								</a><a
									href="http://stackoverflow.com/questions/16942485/avoid-extra-whitespace-when-pasting-vertical-selection"
									class="question-hyperlink">Avoid extra whitespace when
									pasting vertical selection</a>
							</div>
							<div class="spacer">
								<a href="http://stackoverflow.com/q/17791165"
									title="Vote score (upvotes - downvotes)">
									<div class="answer-votes answered-accepted default">1</div>
								</a><a
									href="http://stackoverflow.com/questions/17791165/how-can-i-join-shiftj-the-next-n-words-to-current-line-instead-of-the-entire"
									class="question-hyperlink">How can I join (Shift+J) the
									next N words to current line, instead of the entire line?</a>
							</div>
							<div class="spacer">
								<a href="http://stackoverflow.com/q/21565969"
									title="Vote score (upvotes - downvotes)">
									<div class="answer-votes answered-accepted default">1</div>
								</a><a
									href="http://stackoverflow.com/questions/21565969/copy-and-paste-between-gvim-instances"
									class="question-hyperlink">Copy and Paste Between GVIM
									Instances</a>
							</div>

						</div>
					</div>

					<div id="hot-network-questions" class="module">
						<h4>
							<a href="http://stackexchange.com/questions?tab=hot"
								class="js-gps-track"
								data-gps-track="posts_hot_network.click({ item_type:1, location:11 })">
								Hot Network Questions </a>
						</h4>
						<ul>
							<li>
								<div class="favicon favicon-ell"
									title="English Language Learners Stack Exchange"></div> <a
								href="http://ell.stackexchange.com/questions/42713/im-too-tired-to-drive-grammatical-but-im-tired-to-drive-ungrammatical-how"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:481 }); posts_hot_network.click({ item_type:2, location:11 })">
									I'm too tired to drive -grammatical BUT I'm tired to drive
									-ungrammatical. How? </a>

							</li>
							<li>
								<div class="favicon favicon-math"
									title="Mathematics Stack Exchange"></div> <a
								href="http://math.stackexchange.com/questions/1061477/prove-by-induction-that-an-expression-is-divisible-by-11"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:69 }); posts_hot_network.click({ item_type:2, location:11 })">
									Prove by induction that an expression is divisible by 11 </a>

							</li>
							<li>
								<div class="favicon favicon-superuser" title="Super User"></div>
								<a
								href="http://superuser.com/questions/850766/downloading-software-without-bloatware"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:3 }); posts_hot_network.click({ item_type:2, location:11 })">
									Downloading software without bloatware </a>

							</li>
							<li>
								<div class="favicon favicon-ell"
									title="English Language Learners Stack Exchange"></div> <a
								href="http://ell.stackexchange.com/questions/42722/what-do-we-call-ketchup-cheesy-dip-oregano-and-things-the-like-as-they"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:481 }); posts_hot_network.click({ item_type:2, location:11 })">
									What do we call 'ketchup', 'cheesy dip' , 'oregano' and things
									the like as they aren't side dishes? </a>

							</li>
							<li>
								<div class="favicon favicon-stats" title="Cross Validated"></div>
								<a
								href="http://stats.stackexchange.com/questions/128616/whats-a-real-world-applicative-example-of-overfitting"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:65 }); posts_hot_network.click({ item_type:2, location:11 })">
									What's a real-world applicative example of "overfitting"? </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-math"
									title="Mathematics Stack Exchange"></div> <a
								href="http://math.stackexchange.com/questions/1061357/ways-to-write-50"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:69 }); posts_hot_network.click({ item_type:2, location:11 })">
									Ways to write "50" </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-travel"
									title="Travel Stack Exchange"></div> <a
								href="http://travel.stackexchange.com/questions/39598/traveling-to-amish-country"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:273 }); posts_hot_network.click({ item_type:2, location:11 })">
									Traveling to Amish country </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-space"
									title="Space Exploration Stack Exchange"></div> <a
								href="http://space.stackexchange.com/questions/6281/why-not-build-saturn-vs-again"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:508 }); posts_hot_network.click({ item_type:2, location:11 })">
									Why not build Saturn V's again? </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-codegolf"
									title="Programming Puzzles &amp; Code Golf Stack Exchange"></div>
								<a
								href="http://codegolf.stackexchange.com/questions/42242/recognise-stack-exchange-sites-by-their-icon"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:200 }); posts_hot_network.click({ item_type:2, location:11 })">
									Recognise Stack Exchange Sites by Their Icon </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-academia"
									title="Academia Stack Exchange"></div> <a
								href="http://academia.stackexchange.com/questions/33040/can-we-buy-licenses-for-e-books-and-lend-them-to-students"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:415 }); posts_hot_network.click({ item_type:2, location:11 })">
									Can we buy licenses for e-books and lend them to students? </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-math"
									title="Mathematics Stack Exchange"></div> <a
								href="http://math.stackexchange.com/questions/1061542/riddle-that-involves-elementary-geometry"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:69 }); posts_hot_network.click({ item_type:2, location:11 })">
									riddle that involves elementary geometry </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-gis"
									title="Geographic Information Systems Stack Exchange"></div> <a
								href="http://gis.stackexchange.com/questions/125213/which-equal-area-projection-to-use-for-uk"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:79 }); posts_hot_network.click({ item_type:2, location:11 })">
									Which equal-area projection to use for UK? </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-anime"
									title="Anime &amp; Manga Stack Exchange"></div> <a
								href="http://anime.stackexchange.com/questions/15628/how-does-this-calendar-thing-even-work"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:477 }); posts_hot_network.click({ item_type:2, location:11 })">
									How does this calendar thing even work? </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-worldbuilding"
									title="Worldbuilding Stack Exchange"></div> <a
								href="http://worldbuilding.stackexchange.com/questions/5301/is-asteroid-mining-safe"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:579 }); posts_hot_network.click({ item_type:2, location:11 })">
									Is Asteroid Mining safe? </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-photo"
									title="Photography Stack Exchange"></div> <a
								href="http://photo.stackexchange.com/questions/57397/can-the-canon-600d-take-images-one-stop-up-down-for-creating-an-hdr-image"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:61 }); posts_hot_network.click({ item_type:2, location:11 })">
									Can the Canon 600D take images one stop up &amp; down for
									creating an HDR image? </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-aviation"
									title="Aviation Stack Exchange"></div> <a
								href="http://aviation.stackexchange.com/questions/10097/is-it-ok-to-move-a-light-aircraft-by-pushing-or-pulling-the-propeller"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:528 }); posts_hot_network.click({ item_type:2, location:11 })">
									Is it OK to move a light aircraft by pushing or pulling the
									propeller? </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-superuser" title="Super User"></div>
								<a
								href="http://superuser.com/questions/849845/what-is-the-value-of-md5-checksums-if-the-md5-hash-itself-could-potentially-also"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:3 }); posts_hot_network.click({ item_type:2, location:11 })">
									What is the value of MD5 checksums if the MD5 hash itself could
									potentially also have been manipulated? </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-english"
									title="English Language &amp; Usage Stack Exchange"></div> <a
								href="http://english.stackexchange.com/questions/212695/word-that-means-having-eaten-ones-fill"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:97 }); posts_hot_network.click({ item_type:2, location:11 })">
									Word that means having eaten one's fill </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-superuser" title="Super User"></div>
								<a
								href="http://superuser.com/questions/851048/can-my-landlord-access-my-personal-network-because-he-controls-the-upstream-conn"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:3 }); posts_hot_network.click({ item_type:2, location:11 })">
									Can my landlord access my personal network because he controls
									the upstream connection? </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-math"
									title="Mathematics Stack Exchange"></div> <a
								href="http://math.stackexchange.com/questions/1059235/great-contributions-to-mathematics-by-older-mathematicians"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:69 }); posts_hot_network.click({ item_type:2, location:11 })">
									Great contributions to mathematics by older mathematicians </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-worldbuilding"
									title="Worldbuilding Stack Exchange"></div> <a
								href="http://worldbuilding.stackexchange.com/questions/5214/precious-materials-in-a-galactic-empire"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:579 }); posts_hot_network.click({ item_type:2, location:11 })">
									Precious Materials in a Galactic Empire </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-bicycles"
									title="Bicycles Stack Exchange"></div> <a
								href="http://bicycles.stackexchange.com/questions/26065/cyclocross-bike-as-fast-as-a-roadbike"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:126 }); posts_hot_network.click({ item_type:2, location:11 })">
									Cyclocross bike as fast as a roadbike? </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-scifi"
									title="Science Fiction &amp; Fantasy Stack Exchange"></div> <a
								href="http://scifi.stackexchange.com/questions/74438/why-is-the-stardate-explicitly-mentioned-when-creating-a-new-captains-log"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:186 }); posts_hot_network.click({ item_type:2, location:11 })">
									Why is the stardate explicitly mentioned when creating a new
									Captain's log? </a>

							</li>
							<li class="dno js-hidden">
								<div class="favicon favicon-scifi"
									title="Science Fiction &amp; Fantasy Stack Exchange"></div> <a
								href="http://scifi.stackexchange.com/questions/74362/where-was-the-rebel-fleets-rendezvous-point-at-the-end-of-the-empire-strikes-ba"
								class="js-gps-track"
								data-gps-track="site.switch({ item_type:11, target_site:186 }); posts_hot_network.click({ item_type:2, location:11 })">
									Where was the Rebel fleet's rendezvous point at the end of The
									Empire Strikes Back? </a>

							</li>
						</ul>

						<a
							href="http://stackoverflow.com/questions/27420164/paste-data-at-the-start-of-line-in-visual-mode#"
							class="show-more js-show-more js-gps-track"
							data-gps-track="posts_hot_network.click({ item_type:3, location:11 })">
							more hot questions </a>
					</div>
-->
				</div>

				<div id="feed-link">
					<div id="feed-link-text">
						<a href="http://stackoverflow.com/feeds/question/27420164"
							title="feed of this question and its answers"> <span
							class="feed-icon"></span>question feed
						</a>
					</div>
				</div>
				<script>
StackExchange.ready(function(){$.get('/posts/27420164/ivc/981d');});
</script>
				<noscript>&lt;div&gt;&lt;img
					src="/posts/27420164/ivc/981d" class="dno" alt="" width="0"
					height="0"&gt;&lt;/div&gt;</noscript>
				<div style="display: none" id="prettify-lang"></div>
			</div>



		</div>
	</div>
	<div id="footer" class="categories">
		<div class="footerwrap">
			<div id="footer-menu">
				<div class="top-footer-links">
					<a href="http://stackoverflow.com/tour">tour</a> <a
						href="http://stackoverflow.com/help">help</a> <a
						href="http://blog.stackoverflow.com/?blb=1">blog</a> <a
						href="http://chat.stackoverflow.com/">chat</a> <a
						href="http://data.stackexchange.com/">data</a> <a
						href="http://stackexchange.com/legal">legal</a> <a
						href="http://stackexchange.com/legal/privacy-policy">privacy
						policy</a> <a href="http://stackexchange.com/work-here">work here</a>
					<a href="http://stackexchange.com/mediakit">advertising info</a> <a
						onClick="StackExchange.switchMobile('on', '/users/login?returnurl=%2fmobile%2fon')">mobile</a> <b><a
						href="http://stackoverflow.com/contact">contact us</a></b> <b><a
						href="http://meta.stackoverflow.com/">feedback</a></b>

				</div>
				<div id="footer-sites">
					<table>
						<tbody>
							<tr>
								<th colspan="3">Technology</th>
								<th>Life / Arts</th>
								<th>Culture / Recreation</th>
								<th>Science</th>
								<th>Other</th>
							</tr>
							<tr>
								<td>
									<ol>
										<li><a href="http://stackoverflow.com/"
											title="professional and enthusiast programmers">Stack
												Overflow</a></li>
										<li><a href="http://serverfault.com/"
											title="professional system and network administrators">Server
												Fault</a></li>
										<li><a href="http://superuser.com/"
											title="computer enthusiasts and power users">Super User</a></li>
										<li><a href="http://webapps.stackexchange.com/"
											title="power users of web applications">Web Applications</a></li>
										<li><a href="http://askubuntu.com/"
											title="Ubuntu users and developers">Ask Ubuntu</a></li>
										<li><a href="http://webmasters.stackexchange.com/"
											title="pro webmasters">Webmasters</a></li>
										<li><a href="http://gamedev.stackexchange.com/"
											title="professional and independent game developers">Game
												Development</a></li>
										<li><a href="http://tex.stackexchange.com/"
											title="users of TeX, LaTeX, ConTeXt, and related typesetting systems">TeX
												- LaTeX</a></li>
									</ol>
								</td>
								<td><ol>
										<li><a href="http://programmers.stackexchange.com/"
											title="professional programmers interested in conceptual questions about software development">Programmers</a></li>
										<li><a href="http://unix.stackexchange.com/"
											title="users of Linux, FreeBSD and other Un*x-like operating systems.">Unix
												&amp; Linux</a></li>
										<li><a href="http://apple.stackexchange.com/"
											title="power users of Apple hardware and software">Ask
												Different (Apple)</a></li>
										<li><a href="http://wordpress.stackexchange.com/"
											title="WordPress developers and administrators">WordPress
												Development</a></li>
										<li><a href="http://gis.stackexchange.com/"
											title="cartographers, geographers and GIS professionals">Geographic
												Information Systems</a></li>
										<li><a href="http://electronics.stackexchange.com/"
											title="electronics and electrical engineering professionals, students, and enthusiasts">Electrical
												Engineering</a></li>
										<li><a href="http://android.stackexchange.com/"
											title="enthusiasts and power users of the Android operating system">Android
												Enthusiasts</a></li>
										<li><a href="http://security.stackexchange.com/"
											title="Information security professionals">Information
												Security</a></li>
									</ol></td>
								<td><ol>
										<li><a href="http://dba.stackexchange.com/"
											title="database professionals who wish to improve their database skills and learn from others in the community">Database
												Administrators</a></li>
										<li><a href="http://drupal.stackexchange.com/"
											title="Drupal developers and administrators">Drupal
												Answers</a></li>
										<li><a href="http://sharepoint.stackexchange.com/"
											title="SharePoint enthusiasts">SharePoint</a></li>
										<li><a href="http://ux.stackexchange.com/"
											title="user experience researchers and experts">User
												Experience</a></li>
										<li><a href="http://mathematica.stackexchange.com/"
											title="users of Mathematica">Mathematica</a></li>
										<li><a href="http://salesforce.stackexchange.com/"
											title="Salesforce administrators, implementation experts, developers and anybody in-between">Salesforce</a></li>

										<li><a href="http://stackexchange.com/sites#technology"
											class="more"> more (13) </a></li>
									</ol></td>
								<td>
									<ol>
										<li><a href="http://photo.stackexchange.com/"
											title="professional, enthusiast and amateur photographers">Photography</a></li>
										<li><a href="http://scifi.stackexchange.com/"
											title="science fiction and fantasy enthusiasts">Science
												Fiction &amp; Fantasy</a></li>
										<li><a href="http://graphicdesign.stackexchange.com/"
											title="Graphic Design professionals, students, and enthusiasts">Graphic
												Design</a></li>
										<li><a href="http://cooking.stackexchange.com/"
											title="professional and amateur chefs">Seasoned Advice
												(cooking)</a></li>
										<li><a href="http://diy.stackexchange.com/"
											title="contractors and serious DIYers">Home Improvement</a></li>
										<li><a href="http://money.stackexchange.com/"
											title="people who want to be financially literate">Personal
												Finance &amp; Money</a></li>
										<li><a href="http://academia.stackexchange.com/"
											title="academics and those enrolled in higher education">Academia</a></li>

										<li><a href="http://stackexchange.com/sites#lifearts"
											class="more"> more (10) </a></li>
									</ol>
								</td>
								<td>
									<ol>
										<li><a href="http://english.stackexchange.com/"
											title="linguists, etymologists, and serious English language enthusiasts">English
												Language &amp; Usage</a></li>
										<li><a href="http://skeptics.stackexchange.com/"
											title="scientific skepticism">Skeptics</a></li>
										<li><a href="http://judaism.stackexchange.com/"
											title="those who base their lives on Jewish law and tradition and anyone interested in learning more">Mi
												Yodeya (Judaism)</a></li>
										<li><a href="http://travel.stackexchange.com/"
											title="road warriors and seasoned travelers">Travel</a></li>
										<li><a href="http://christianity.stackexchange.com/"
											title="committed Christians, experts in Christianity and those interested in learning more">Christianity</a></li>
										<li><a href="http://gaming.stackexchange.com/"
											title="passionate videogamers on all platforms">Arqade
												(gaming)</a></li>
										<li><a href="http://bicycles.stackexchange.com/"
											title="people who build and repair bicycles, people who train cycling, or commute on bicycles">Bicycles</a></li>
										<li><a href="http://rpg.stackexchange.com/"
											title="gamemasters and players of tabletop, paper-and-pencil role-playing games">Role-playing
												Games</a></li>

										<li><a
											href="http://stackexchange.com/sites#culturerecreation"
											class="more"> more (21) </a></li>
									</ol>
								</td>
								<td>
									<ol>
										<li><a href="http://math.stackexchange.com/"
											title="people studying math at any level and professionals in related fields">Mathematics</a></li>
										<li><a href="http://stats.stackexchange.com/"
											title="people interested in statistics, machine learning, data analysis, data mining, and data visualization">Cross
												Validated (stats)</a></li>
										<li><a href="http://cstheory.stackexchange.com/"
											title="theoretical computer scientists and researchers in related fields">Theoretical
												Computer Science</a></li>
										<li><a href="http://physics.stackexchange.com/"
											title="active researchers, academics and students of physics">Physics</a></li>
										<li><a href="http://mathoverflow.net/"
											title="professional mathematicians">MathOverflow</a></li>

										<li><a href="http://stackexchange.com/sites#science"
											class="more"> more (7) </a></li>
									</ol>
								</td>
								<td>
									<ol>
										<li><a href="http://stackapps.com/"
											title="apps, scripts, and development with the Stack Exchange API">Stack
												Apps</a></li>
										<li><a href="http://meta.stackexchange.com/"
											title="meta-discussion of the Stack Exchange family of Q&amp;A websites">Meta
												Stack Exchange</a></li>
										<li><a href="http://area51.stackexchange.com/"
											title="proposing new sites in the Stack Exchange network">Area
												51</a></li>
										<li><a
											href="http://careers.stackoverflow.com/?utm_source=stackoverflow.com&utm_medium=site-ui&utm_campaign=footerlink">Stack
												Overflow Careers</a></li>

									</ol>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div id="copyright">
				site design / logo © 2014 stack exchange inc; user contributions
				licensed under <a
					href="http://creativecommons.org/licenses/by-sa/3.0/" rel="license">cc
					by-sa 3.0</a> with <a
					href="http://blog.stackoverflow.com/2009/06/attribution-required/"
					rel="license">attribution required</a>
			</div>
			<div id="svnrev">rev 2014.12.10.2097</div>

		</div>
	</div>
	<noscript>&lt;div id="noscript-warning"&gt;Stack Overflow
		works best with JavaScript enabled&lt;img
		src="http://pixel.quantserve.com/pixel/p-c1rF4kxgLUzNc.gif" alt=""
		class="dno"&gt;&lt;/div&gt;</noscript>
	<script>var p = "http", d = "static"; if (document.location.protocol == "https:") { p += "s"; d = "engine"; } var z = document.createElement("script"); z.type = "text/javascript"; z.async = true; z.src = p + "://" + d + ".adzerk.net/ados.js"; var s = document.getElementsByTagName("script")[0]; s.parentNode.insertBefore(z, s);</script>
	<script>
    var ados = ados || {};
    ados.run = ados.run || [];
    ados.run.push(function () { ados_setKeywords('vim,vi');; ados_load(); });         
</script>

	<script>
        (function (i, s, o, g, r, a, m) {
            i['GoogleAnalyticsObject'] = r; i[r] = i[r] || function () { (i[r].q = i[r].q || []).push(arguments) }, i[r].l = 1 * new Date(); a = s.createElement(o),
            m = s.getElementsByTagName(o)[0]; a.async = 1; a.src = g; m.parentNode.insertBefore(a, m);
        })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');
        ga('create', 'UA-5620270-1');        
        
        ga('set', 'dimension2', '|vim|vi|');         
        ga('send', 'pageview');
        var _qevents = _qevents || [],
            _comscore = _comscore || [];
        (function () {
            var ssl='https:'==document.location.protocol,
                s=document.getElementsByTagName('script')[0],
                qc=document.createElement('script');
            qc.async=true;
            qc.src=(ssl?'https://secure':'http://edge')+'.quantserve.com/quant.js';
            s.parentNode.insertBefore(qc, s);
            var sc=document.createElement('script');
            sc.async=true;
            sc.src=(ssl?'https://sb':'http://b') + '.scorecardresearch.com/beacon.js';
            s.parentNode.insertBefore(sc, s);
        })();
        _comscore.push({ c1: "2", c2: "17440561" });
        _qevents.push({ qacct: "p-c1rF4kxgLUzNc" });
    </script>


<!-- 	<div id="noscript-warning">Stack Overflow requires external
		JavaScript from another domain, which is blocked or failed to load.</div> -->
</body>
</html>