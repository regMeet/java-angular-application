var gulp = require('gulp');
var uglify = require('gulp-uglify');
var concat = require('gulp-concat');
var rev = require('gulp-rev');
var filesize = require('gulp-filesize');
var usemin = require('gulp-usemin');
var ngmin = require('gulp-ngmin');
var csso = require('gulp-csso');
var rename = require("gulp-rename");
var jshint = require('gulp-jshint');
var clean = require('gulp-clean');

gulp.task('lint', function () {
  return gulp.src('./src/*.js')
    .pipe(jshint('.jshintrc'))
    .pipe(jshint.reporter('jshint-stylish'));
});

gulp.task('clean', function () {
  return gulp.src('./dist', { read: false })
    .pipe(clean());
});

gulp.task('build', ['test', 'clean'], function () {
  return gulp.src('./src/contra.js')
    .pipe(gulp.dest('./dist'))
    .pipe(rename('contra.min.js'))
    .pipe(uglify())
    .pipe(size())
    .pipe(gulp.dest('./dist'));
});

gulp.task('build2', function () {
	// copy original index.html to index-dev.html
	gulp.src('src/main/webapp/index.html').pipe(rename('index-dev.html')).pipe(
			gulp.dest('target/overlay'));

	// compress resources from index.html
	gulp.src('src/main/webapp/index.html').pipe(usemin({
		vendor : [ filesize(), uglify(), rev(), filesize() ],
		scripts : [ filesize(), ngmin(), uglify(), rev(), filesize() ],
		stylesheets : [ 'concat', filesize(), csso(), rev(), filesize() ]
	})).pipe(gulp.dest('target/overlay'));
});


gulp.task('default', ['build']);

gulp.on('err', function(err) {
	throw err;
});


gulp.task('test', ['lint', 'mocha']);
gulp.task('ci', ['build']);

