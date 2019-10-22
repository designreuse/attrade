<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="">
    <link rel="icon" type="image/png" href="ico/favicon-32.png"/>
    <!-- Icon For iOS 2.0+ and Android 2.1+ -->
    <link rel="apple-touch-icon-precomposed" href="ico/favicon-152.png">
    <!-- Icon For IE10 Metro -->
    <meta name="msapplication-TileColor" content="#fff">
    <meta name="msapplication-TileImage" content="ico/favicon-144.png">

    <title>Bootstrap Template</title>

    <!-- Bootstrap Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <!-- Font-Awesome -->
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        /*ZoomIn Hover Effect*/
        .hover-zoomin a {
            display: block;
            position: relative;
            overflow: hidden;
        }
        .hover-zoomin img {
            width: 100%;
            height: auto;
            transition: all 0.5s ease-in-out;
        }
        .hover-zoomin:hover img {
            -webkit-transform: scale(1.2);
            transform: scale(1.2);
        }

        /*Fade Hover Effect*/
        .hover-fade a {
            display: block;
            position: relative;
            overflow: hidden;
            background-color: #333;
        }
        .hover-fade img {
            width: 100%;
            height: auto;
            transition: all 0.5s ease-in-out;
        }
        .hover-fade:hover img {
            -webkit-transform: scale(1.2);
            transform: scale(1.2);
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=0.5)";
            filter: alpha(opacity=0.5);
            opacity: 0.5;
        }

        /*ZoomIn Hover-Mask Effect*/
        .hover-mask a {
            display: block;
            position: relative;
            overflow: hidden;
        }
        .hover-mask img {
            width: 100%;
            height: auto;
            transition: all 0.5s ease-in-out;
        }
        .hover-mask:hover img {
            -webkit-transform: scale(1.2);
            transform: scale(1.2);
        }
        .hover-mask a h2 {
            display: block;
            position: absolute;
            top: 0;
            left: 0;
            margin-top: 0px;
            margin-bottom: 0px;
            height: 100%;
            width: 100%;
            padding: 45% 20px;
            text-align: center;
            background-color: rgba(0,0,0,0.4);
            transition: all 0.5s linear;
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=0)";
            filter: alpha(opacity=0);
            opacity: 0;
        }
        .hover-mask:hover a h2 {
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=100)";
            filter: alpha(opacity=100);
            opacity: 1;
        }
        .hover-mask .glyphicon {
            -webkit-transform: scale(0);
            transform: scale(0);
            color: #fff;
            transition: all 0.5s linear;
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=0)";
            filter: alpha(opacity=0);
            opacity: 0;
        }
        .hover-mask:hover .glyphicon {
            -webkit-transform: scale(1);
            transform: scale(1);
            transition: all 0.5s linear;
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=1)";
            filter: alpha(opacity=1);
            opacity: 1;
        }

        /* Hover Blur Effect */
        .text-white {
            color: #fff;
        }
        .hover-blur a {
            display: block;
            position: relative;
            overflow: hidden;
        }
        .hover-blur img {
            width: 100%;
            height: auto;
            transition: all 0.5s ease-in-out;
        }
        .hover-blur:hover img {
            -webkit-transform: scale(1.2);
            transform: scale(1.2);
            -webkit-filter: grayscale(0.6) blur(1px);
            filter: grayscale(0.6) blur(1px);
        }
        .hover-blur a h2 {
            display: block;
            position: absolute;
            top: 0;
            left: 0;
            margin-top: 0px;
            margin-bottom: 0px;
            height: 100%;
            width: 100%;
            padding: 45% 20px;
            text-align: center;
            background-color: rgba(0,0,0,0.5);
            /*background dotted*/
            background-image: -webkit-repeating-radial-gradient(center center, rgba(0,0,0,.8), rgba(0,0,0,.8) 1px, transparent 1px, transparent 100%);
            background-image: repeating-radial-gradient(center center, rgba(0,0,0,.8), rgba(0,0,0,.8) 1px, transparent 1px, transparent 100%);
            background-size: 3px 3px;
            transition: all 0.5s linear;
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=0)";
            filter: alpha(opacity=0);
            opacity: 0;
        }
        .hover-blur:hover a h2 {
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=100)";
            filter: alpha(opacity=100);
            opacity: 1;
        }
        .hover-blur .text-white {
            -webkit-transform: scale(0);
            transform: scale(0);
            transition: all 0.5s linear;
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=0)";
            filter: alpha(opacity=0);
            opacity: 0;
        }
        .hover-blur:hover .text-white {
            -webkit-transform: scale(1);
            transform: scale(1);
            transition: all 0.5s linear;
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=1)";
            filter: alpha(opacity=1);
            opacity: 1;
        }

        /* Hover Zoomout */
        .hover-zoomout a {
            display: block;
            position: relative;
            overflow: hidden;
        }
        .hover-zoomout img {
            width: 100%;
            height: auto;
            transition: all 0.5s ease-in-out;
            -webkit-transform: scale(1.2);
            transform: scale(1.2);
        }
        .hover-zoomout:hover img {
            -webkit-transform: scale(1);
            transform: scale(1);
        }

        /*Hover Blurout Effect*/
        .hover-blurout a {
            display: block;
            position: relative;
            overflow: hidden;
        }
        .hover-blurout img {
            width: 100%;
            height: auto;
            transition: all 0.5s ease-in-out;
            -webkit-transform: scale(1.2);
            transform: scale(1.2);
        }
        .hover-blurout:hover img {
            -webkit-transform: scale(1);
            transform: scale(1);
            /*Blur Filtr*/
            -webkit-filter: grayscale(0.6) blur(1px);
            -moz-filter: grayscale(0.6) blur(1px);
            -o-filter: grayscale(0.6) blur(1px);
            -ms-filter:progid:DXImageTransform.Microsoft.Blur(pixelRadius=2);
            filter: progid:DXImageTransform.Microsoft.blur(pixelradius=2);
            -webkit-filter: grayscale(0.6) blur(2px);
            filter: grayscale(0.6) blur(2px);
        }
        .hover-blurout a h2 {
            display: block;
            position: absolute;
            top: 0;
            left: 0;
            margin-top: 0px;
            margin-bottom: 0px;
            height: 100%;
            width: 100%;
            padding: 45% 20px;
            text-align: center;
            background-color: rgba(0,0,0,0.4);
            /*background dotted*/
            background-image: -webkit-repeating-radial-gradient(center center, rgba(0,0,0,.8), rgba(0,0,0,.8) 1px, transparent 1px, transparent 100%);
            background-image: repeating-radial-gradient(center center, rgba(0,0,0,.8), rgba(0,0,0,.8) 1px, transparent 1px, transparent 100%);
            background-size: 3px 3px;
            transition: all 0.5s linear;
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=0)";
            filter: alpha(opacity=0);
            opacity: 0;
        }
        .hover-blurout:hover a h2 {
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=100)";
            filter: alpha(opacity=100);
            opacity: 1;
        }
        .hover-blurout .glyphicon {
            -webkit-transform: scale(0);
            transform: scale(0);
            color: #fff;
            transition: all 0.5s linear;
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=0)";
            filter: alpha(opacity=0);
            opacity: 0;
        }
        .hover-blurout:hover .glyphicon {
            -webkit-transform: scale(1);
            transform: scale(1);
            transition: all 0.5s linear;
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=1)";
            filter: alpha(opacity=1);
            opacity: 1;
        }
        /* ---------Hover Overlay text Effect--------- */

        .overlay-item {
            display: block;
            position: relative;
            overflow: hidden;
            text-align: center;

        }
        .overlay-item .mask {
            display: block;
            position: absolute;
            overflow: hidden;
            top: 0;
            left: 0;
            height: 100%;
        }
        .overlay-item img {
            display: block;
            width: 100%;
            height: auto;
            x-margin-right: auto;
            x-margin-left: auto;
        }
        .overlay-item h3 {
            text-transform: uppercase;
            text-align: center;
            position: relative;
            padding: 10px;
            margin: 0px 0px 20px 0px;
            display: block;
            background-color: #333;
            color: #fff;
        }
        .overlay-item p {
            text-align: left;
        }
        .overlay-item a.btn {
            margin: 10px 0;
        }

        .overlay-effect img {
            -webkit-transform: scaleY(1);
            transform: scaleY(1);
            transition: all 0.7s ease-in-out;
        }
        .overlay-effect .mask {
            background-color: rgba(255, 255, 255, 0.3);
            transition: all 0.5s linear;
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=0)";
            filter: alpha(opacity=0);
            opacity: 0;
        }
        .overlay-effect h3 {
            -webkit-transform: scale(0);
            transform: scale(0);
            transition: all 0.5s linear;
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=0)";
            filter: alpha(opacity=0);
            opacity: 0;
        }
        .overlay-effect p {
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=0)";
            filter: alpha(opacity=0);
            opacity: 0;
            -webkit-transform: scale(0);
            transform: scale(0);
            transition: all 0.5s linear;
        }
        .overlay-effect a.btn {
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=0)";
            filter: alpha(opacity=0);
            opacity: 0;
            -webkit-transform: scale(0);
            transform: scale(0);
            transition: all 0.5s linear;
        }
        .overlay-effect:hover img {
            -webkit-transform: scale(10);
            transform: scale(10);
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=0)";
            filter: alpha(opacity=0);
            opacity: 0;
        }
        .overlay-effect:hover .mask {
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=100)";
            filter: alpha(opacity=100);
            opacity: 1;
        }
        .overlay-effect:hover h3,.overlay-effect:hover p,.overlay-effect:hover a.btn {
            -webkit-transform: scale(1);
            transform: scale(1);
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=100)";
            filter: alpha(opacity=100);
            opacity: 1;
        }
        /*Hover Fall Effect*/
        .fall-item {
            width: 100%;
            height: auto;
            overflow: hidden;
            position: relative;
            text-align: center;
        }
        .fall-item .mask,.fall-item .content {
            width: 100%;
            height: 100%;
            position: absolute;
            overflow: hidden;
            top: 0;
            left: 0;
        }
        .fall-item img {
            display: block;
            position: relative;
            width: 100%;
            height: auto;
        }
        .fall-item h2 {
            text-transform: uppercase;
            color: #fff;
            text-align: center;
            position: relative;
            padding: 10px;
            background: rgba(0, 0, 0, 0.8);
            margin: 0 0 0 0;
        }
        .fall-item p {
            font-style: italic;
            position: relative;
            color: #fff;
            padding: 10px 20px 20px;
            text-align: center;
        }
        .fall-effect .mask {
            background-color: rgba(255, 255, 255, 0.7);
            top: -200px;
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=0)";
            filter: alpha(opacity=0);
            opacity: 0;
            transition: all 0.3s ease-out 0.5s;
        }
        .fall-effect h2 {
            -webkit-transform: translateY(-200px);
            transform: translateY(-200px);
            transition: all 0.2s ease-in-out 0.1s;
        }
        .fall-effect p {
            color: #333;
            -webkit-transform: translateY(-200px);
            transform: translateY(-200px);
            transition: all 0.2s ease-in-out 0.2s;
        }
        .fall-effect a.btn {
            -webkit-transform: translateY(-200px);
            transform: translateY(-200px);
            transition: all 0.2s ease-in-out 0.3s;
        }
        .fall-effect:hover .mask {
            -ms-filter: "progid: DXImageTransform.Microsoft.Alpha(Opacity=100)";
            filter: alpha(opacity=100);
            opacity: 1;
            top: 0px;
            transition-delay: 0s;

        }
        .fall-effect:hover h2 {
            -webkit-transform: translateY(0px);
            transform: translateY(0px);
            transition-delay: 0.4s;
        }
        .fall-effect:hover p {
            -webkit-transform: translateY(0px);
            transform: translateY(0px);
            transition-delay: 0.2s;
        }
        .fall-effect:hover a.btn {
            -webkit-transform: translateY(0px);
            transform: translateY(0px);
            transition-delay: 0s;
        }
    </style>
</head>
<body>

<div class="container">
    <h1 class="text-center text-muted">HOVER-EFFECT CSS</h1>
    <div class="row">
        <div class="col-xs-6 col-sm-3 hover-zoomin">
            <a href="#" title="">
                <img src="http://placeimg.com/260/260/nature/1/" alt=""/>
            </a>
            <h4 class="text-center">Hover-ZoomIn</h4>
        </div>
        <div class="col-xs-6 col-sm-3 hover-fade">
            <a href="#" title="">
                <img src="http://placeimg.com/260/260/nature/2/" alt=""/>
            </a>
            <h4 class="text-center">Hover-Fade</h4>
        </div>
        <div class="col-xs-6 col-sm-3 hover-blur">
            <a href="#" title="">
                <img src="http://placeimg.com/260/260/nature/3/" alt=""/>
                <h2><span class="text-white">Hover Blur</span></h2>
            </a>
            <h4 class="text-center">Hover-Blur</h4>
        </div>
        <div class="col-xs-6 col-sm-3 hover-mask">
            <a href="#" title="">
                <img src="http://placeimg.com/260/260/nature/4/" alt=""/>
                <h2><span class="glyphicon glyphicon-search"></span></h2>
            </a>
            <h4 class="text-center">Hover-Mask</h4>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-6 col-sm-3 hover-zoomout">
            <a href="" title="">
                <img src="http://placeimg.com/260/260/nature/5/" alt="" />
            </a>
            <h4 class="text-center">Hover-Zoomout</h4>
        </div>
        <div class="col-xs-6 col-sm-3 hover-blurout">
            <a href="#" title="">
                <img src="http://placeimg.com/260/260/nature/6/" alt=""/>
                <h2><span class="glyphicon glyphicon-search"></span></h2>
            </a>
            <h4 class="text-center">Hover-Blurout</h4>
        </div>
        <div class="col-xs-6 col-sm-3">
            <div class="overlay-item overlay-effect">
                <img src="http://placeimg.com/260/260/nature/7/" alt="" />
                <div class="mask">
                    <h3>OVERLAY</h3>
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptatibus doloremque natus quidem id.
                    </p>
                    <a href="#" class="btn btn-default">Read More</a>
                </div>
            </div>
            <h4 class="text-center">Overlay Text</h4>
        </div>
        <!-- Hover-Fall Effect-->
        <div class="col-xs-6 col-sm-3">
            <div class="fall-item fall-effect">
                <img src="http://placeimg.com/260/260/nature/8/"/>
                <div class="mask">
                    <h2>Hover Fall</h2>
                    <p>A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart.</p>
                    <a href="#" class="btn btn-default">Read More</a>
                </div>
            </div>
            <h4 class="text-center">Hover-Fall</h4>
        </div>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</body>
</html>