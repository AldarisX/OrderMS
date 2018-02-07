<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset=utf-8/>
    <meta name="viewport"
          content="width=1,height=1,initial-scale=1, maximum-scale=3, minimum-scale=1,user-scalable=no"/>
    <meta name="theme-color" content="#66CCFF">
    <title></title>
    <style>
        * {
            font-family: "Microsoft YaHei", Arial, Helvetica, sans-serif;
        }

        body {
            margin: 0 auto;
            max-width: 90%;
            text-align: center;
            background: #F6F6F6;
            position: fixed;
            top: 14%;
            left: 1.1em;
            right: 1.1em;
        }

        .error_img_con {
            margin-top: 2em;
        }

        .error_img_con img {
            max-width: 100%;
        }

        .error_desc p {
            font-size: 1.5em;
        }

        #desc_msg {
            margin-top: 1.5em;
            color: #ff6a6c;
        }
    </style>
    <script type="text/javascript">
        //当页面加载完成时
        window.onload = function () {
            //获取参数
            let error = getQueryString("error");
            //获得错误信息
            let desc_msg = getMSG(error);
            //拼接图片链接
            let img_Link = error + ".png";
            //判断是否是已知错误
            if (desc_msg === "") {
                desc_msg = "未知错误  我们也不知道该怎么办";
                img_Link = "520.png";
            }
            //设置网页标题
            document.title = desc_msg;
            //设置图片链接
            document.getElementById("error_img").setAttribute("src", img_Link);
            //设置错误信息
            document.getElementById("desc_msg").innerHTML = desc_msg;
        };

        function getMSG(msg) {
            //区分错误信息
            switch (msg) {
                case "400":
                    return "禁止说明事项";
                case "401":
                    return "服务器不想和你说话";
                case "404":
                    return "页面已蒸发";
                case "500":
                    return "程序员已死";
            }
            return "";
        }

        function getQueryString(name) {
            let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            let r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]);
            return null;
        }
    </script>
</head>
<body>
<div class="error_img_con">
    <img id="error_img" src=""/>
</div>
<div class="error_desc">
    <p id="desc_msg">
    </p>
</div>
</body>
</html>