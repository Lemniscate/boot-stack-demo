/**
 * Compiles all our sources into `public` folder.
 * Angular templates will be available as "[details.module].templates" module
 */

var details = {
    module : 'app'
};

var gulp        = require('gulp');
var ngmin       = require('gulp-ngmin');
var ugly        = require('gulp-uglify');
var rename      = require('gulp-rename');
var bower       = require('gulp-bower');
var concat      = require('gulp-concat');
var less        = require('gulp-less');
var html2js     = require('gulp-ng-html2js');
var flatten     = require('gulp-flatten');
var minifyHtml  = require('gulp-minify-html');
var config      = require('./gulp-config.json');

gulp.task('scripts', function() {
  gulp.src(['src/scripts/**/*.js'])
  .pipe(ngmin())
  .pipe(concat('all.js'))
  .pipe(ugly({ outSourceMaps: true }))
  .pipe(gulp.dest('public/scripts'));
});

//gulp-copy
gulp.task('copy', function(){
  //Move Javascript Libraries
  gulp.src(config.libs)
    .pipe(concat('libs.js'))
    .pipe(gulp.dest('public/scripts'));

  gulp.src("bower_components/**/*.js.map")
    .pipe(flatten())
    .pipe(gulp.dest('public/scripts'));

  //TODO: we should minify the css too
  gulp.src(config.libsCss)
    .pipe(concat('libs.css'))
    .pipe(gulp.dest('public/css'));

  //Move fonts
  gulp.src('bower_components/font-awesome-bower/fonts/*')
    .pipe(gulp.dest('public/fonts'));

  //move img
  gulp.src('src/img/*')
    .pipe(gulp.dest('public/css'));

  // copy css
  gulp.src('src/css/*')
    .pipe(concat('all.css'))
    .pipe(gulp.dest('public/css'));
});

//gulp-less
gulp.task('less', function () {
  gulp.src('src/less/app.less')
    .pipe(less())
    .pipe(gulp.dest('public/css'));
});

//gulp-html2js
gulp.task('html2js', function(){
  gulp.src('src/scripts/**/*.tpl.html')
    .pipe(minifyHtml({
      empty: true,
      spare: true,
      quotes: true
    }))
    .pipe(html2js({
      stripPrefix: 'src/scripts/', 
      moduleName: details.module + '.templates'
    }))
    .pipe(concat('templates.js'))
    .pipe(gulp.dest('public/templates'))
    .pipe(rename('templates.min.js'))
    .pipe(ugly())
    .pipe(gulp.dest('public/templates'));
});

//gulp-karma
gulp.task('bower', function() {
  bower();
});

gulp.task('prepare', function() {  
    gulp.run('bower');
});

gulp.task('dev', function(){
  gulp.run('scripts', 'html2js', 'copy', 'less');
  
  gulp.watch('src/scripts/**/*.js', function() {
    gulp.run('scripts');
  });

  gulp.watch('src/scripts/**/*.tpl.html', function(){
    gulp.run('html2js');
  });
  
  gulp.watch('src/less/**', function() {
    gulp.run('less');
  });
});

gulp.task('build', function(){
  gulp.run('scripts', 'html2js', 'copy', 'less');
});