var gulp = require('gulp'),
//    usemin = require('gulp-usemin'),
    wrap = require('gulp-wrap'),

    connect = require('gulp-connect'),
    watch = require('gulp-watch'),
//    minifyCss = require('gulp-minify-css'),
    minifyJs = require('gulp-uglify'),
    concat = require('gulp-concat'),
    less = require('gulp-less'),
    rename = require('gulp-rename'),
    html2js = require('gulp-html2js');
//    minifyHTML = require('gulp-minify-html');

var paths = {
    theme_scripts:  '../admin/assets/theme-js/**/*.*',
    scripts:        '../admin/assets/js/**/*.*',
    styles:         '../admin/assets/css/**/*.*',
    images:         '../admin/assets/img/**/*.*',
    htmltemplates:      '../admin/assets/templates/**/*.html',
    html_templates:     '../admin/app/**/*.html',
    app_js:         '../admin/app/**/*.js',
    index:          '../admin/index.html',
    less:           '../admin/assets/less/*.less',
    bower_fonts:    'src/components/**/*.{ttf,eot,woff,eof,svg}'
};

/**
 * Handle bower components from index
 */
gulp.task('usemin', function() {
    return gulp.src(paths.index)
//        .pipe(usemin({
//            js: [minifyJs(), 'concat'],
//            css: [minifyCss({keepSpecialComments: 0}), 'concat'],
//        }))
        .pipe(gulp.dest('../src/main/webapp/'));
});


/**
 * Copy assets
 */
gulp.task('build-assets', ['copy-bower_fonts']);

gulp.task('copy-bower_fonts', function() {
    return gulp.src(paths.bower_fonts)
        .pipe(rename({
            dirname: '/fonts'
        }))
        .pipe(gulp.dest('../src/main/webapp'));
});

/**
 * Handle custom files
 */
gulp.task('build-custom', ['html-template','copy-components', 'custom-images', 'app-js','custom-js', 'theme-js', 'lib-less' ,'custom-less', 'custom-templates']);


gulp.task('copy-components', function() {
    return gulp.src('../admin/assets/components/**/*.*')
        .pipe(gulp.dest('../src/main/webapp/components'));
});


gulp.task('custom-images', function() {
    return gulp.src(paths.images)
        .pipe(gulp.dest('../src/main/webapp/img'));
});


gulp.task('app-js', function() {
    return gulp.src(paths.app_js)
        //.pipe(minifyJs())
        .pipe(concat('st-openpms.concat.js'))
        .pipe(gulp.dest('../src/main/webapp/js'));
});

gulp.task('theme-js', function() {
    return gulp.src(paths.theme_scripts)
        //.pipe(minifyJs())
        .pipe(concat('dashboard.min.js'))
        .pipe(gulp.dest('../src/main/webapp/js'));
});

gulp.task('custom-js', function() {
    return gulp.src(paths.scripts)
   //     .pipe(minifyJs())
   //     .pipe(concat('openpms.concat.js'))
        .pipe(gulp.dest('../src/main/webapp/js'));
});

gulp.task('custom-less', function() {
    return gulp.src(paths.less)
        .pipe(less())
        .pipe(gulp.dest('../src/main/webapp/css'));
});

gulp.task('lib-less', function() {
    return gulp.src(paths.less)
        .pipe(less())
        .pipe(gulp.dest('../src/main/webapp/css'));
});


gulp.task('custom-templates', function() {
    return gulp.src(paths.htmltemplates)
       // .pipe(minifyHTML())
        .pipe(gulp.dest('../src/main/webapp/templates'));
});


gulp.task('html-template', function(url) {
    return gulp.src(paths.html_templates)
        .pipe(html2js(
                {
                    base : '../admin/app/',
                    outputModuleName: 'RedCA',
                    useStrict: true
        }

        ))
        .pipe(concat('openpms-st-test.templates.js'))
        .pipe(gulp.dest('../src/main/webapp'));
});


/**
 * Watch custom files
 */
gulp.task('watch', function() {
    gulp.watch([paths.html_templates], ['html-template']);
    gulp.watch([paths.images], ['custom-images']);
    gulp.watch([paths.styles], ['custom-less']);
    gulp.watch([paths.scripts], ['custom-js']);
    gulp.watch([paths.theme_scripts], ['theme-js']);
    gulp.watch([paths.htmltemplates], ['custom-templates']);
    gulp.watch([paths.index], ['usemin']);
    gulp.watch([paths.app_js], ['app-js']);
});

/**
 * Live reload server
 */
gulp.task('webserver', function() {
    connect.server({
        root: '../src/main/webapp/',
        livereload: true,
        port: 5555
    });
});


 gulp.task('livereload', function() {
   // gulp.src(['../src/main/webapp/**/*.*'])
   //     .pipe(watch())
   //     .pipe(connect.reload());
});

/**
 * Gulp tasks
 */
gulp.task('build', ['usemin', 'build-assets', 'build-custom']);
gulp.task('default', ['build', 'webserver', 'livereload', 'watch']);