var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var rev = require('gulp-rev');
var filesize = require('gulp-filesize');
var usemin = require('gulp-usemin');
var ngmin = require('gulp-ngmin');
var csso = require('gulp-csso');
var rename = require("gulp-rename");
var jshint = require('gulp-jshint');
var connect = require('gulp-connect');
var watch = require('gulp-watch');
var minifyHTML = require('gulp-minify-html');


var webapp_path = 'src/main/webapp/';
var components_path = webapp_path + 'components/'
var assets_path = webapp_path + 'assets/'
var shared_path = webapp_path + 'shared/'

var files = [
    '!src/main/webapp/components/**/*_test.js', // Exclude test files
    components_path + '**/*.js'
];


var paths = {
	    assets_styles:      assets_path + 'css/**/*.*',
	    assets_img:         assets_path + '{img/**/*.*,img/*.*}',
	    assets_js:         	assets_path + 'js/**/*.*',
	    assets_libs:        assets_path + 'libs/**/*.*',
	    html_templates:     components_path + '**/*.html',
	    app_scripts:        components_path + '**/*.js',
	    shared:				shared_path + '**/*.*',
	    shared_templates:	shared_path + '*.html',
	    shared_directives:	shared_path + 'directives/*.js',
	    app_js:         	webapp_path + 'app.js',
	    app_css:         	webapp_path + 'app.css',
	    app: 	        	webapp_path + 'app.{js,css}',
	    index:          	webapp_path + '/index.html',
	    icon:          		webapp_path + '/favicon.png',
};

gulp.task('lint', function() {
	  return gulp.src(files)
	    .pipe(jshint('.jshintrc'))
        .pipe(jshint.reporter('jshint-stylish'))
	    .pipe(jshint.reporter('fail'));
});

// Register tasks
gulp.task('concat-min', function() {
    return gulp.src(files)
        .pipe(concat('script.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest('dist'));
});

gulp.task('min-concat', function() {
    return gulp.src(files)
        .pipe(uglify())
        .pipe(concat('script.min.js'))
        .pipe(gulp.dest('dist'));
});

gulp.task('keep-index-html', function() {
	// copy original index.html to index-dev.html
	gulp.src('src/main/webapp/index.html')
		.pipe(rename('index-dev.html'))
		.pipe(gulp.dest('target/overlay'));
});

gulp.task('minify-css-js', function() {
	// compress resources from index.html
	gulp.src('src/main/webapp/index.html')
		.pipe(usemin({
			vendor: [filesize(),
//			         uglify(),
//			         rev(),
			         filesize()],
			scripts: [filesize(),
//			          ngmin(),
//			          uglify(),
//			          rev(),
			          filesize()],
			stylesheets: ['concat', filesize(), csso(), rev(), filesize()]
		}))
		.pipe(gulp.dest('target/overlay'));
});

gulp.task('copy-templates', function() {
    return gulp.src(paths.html_templates)
        .pipe(minifyHTML())
        .pipe(gulp.dest('target/overlay/templates'));
});

gulp.task('copy-shared-templates', function() {
    return gulp.src(paths.shared_templates)
        .pipe(minifyHTML())
        .pipe(gulp.dest('target/overlay/templates'));
});

gulp.task('copy-images', function() {
    return gulp.src(paths.assets_img)
        .pipe(gulp.dest('target/overlay/images'));
});

gulp.task('copy-icon', function() {
    return gulp.src(paths.icon)
        .pipe(gulp.dest('target/overlay'));
});

/**
 * Watch custom files
 */
gulp.task('watch', function() {
    gulp.watch([paths.html_templates], ['copy-templates']);
    gulp.watch([paths.shared_templates], ['copy-shared-templates']);
    gulp.watch([paths.app_scripts, paths.index ], ['keep-index-html', 'minify-css-js']);
    gulp.watch([paths.app], ['minify-css-js']);
    gulp.watch([paths.shared_directives], ['minify-css-js']);
    
    // gulp-plumber
    // https://github.com/floatdrop/gulp-plumber
});


/**
 * Live reload server
 */
gulp.task('webserver', function() {
    connect.server({
        root: 'target/overlay',
//        livereload: true,
	port: 9090
    });
});

gulp.on('err', function (err) {
	throw err;
});

gulp.task('build', ['lint', 'keep-index-html', 'minify-css-js', 'copy-templates', 'copy-shared-templates', 'copy-images', 'copy-icon']);
gulp.task('default', ['build']);
gulp.task('start', ['webserver', 'watch']);
