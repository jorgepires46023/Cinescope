module.exports = {
    mode: 'development',
    resolve: {
        extensions: [".js", ".ts", ".tsx"]
    },
    devServer: {
        hot: false,
        port: 8080, //Website PORT
        historyApiFallback: true, 
        proxy: {
            '/api': { //Solves CORS policy error
                target: 'http://localhost:9000',
                pathRewrite: {"^/api": ""},
            }
        },
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/
            }
        ]
    }
}