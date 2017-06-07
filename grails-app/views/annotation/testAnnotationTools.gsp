<!DOCTYPE html>
<html lang="en">

<head>
    <title>Test Annotation Tool</title>
    <meta charset="utf-8">
    <link href='https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons' rel="stylesheet" type="text/css">
    <link rel="icon" type="image/png" href="public/favicon.ico" sizes="32x32">
</head>

<body>
<div class='canvas-container'>
    <v-container fluid>
        <v-row>
            <v-col xs6 offset-xs3>
                <canvas id="view" class="elevation-3"></canvas>
            </v-col>
        </v-row>
    </v-container>
</div>

<div id="app-container">
    <div id="app"></div>
</div>

<asset:javascript src="build.js"/>
</body>

</html>