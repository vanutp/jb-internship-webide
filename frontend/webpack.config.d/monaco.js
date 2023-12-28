const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin');

config.module.rules.push(
  {
    test: /\.css$/i,
    use: ['style-loader', 'css-loader'],
  },
  {
    test: /\.ttf$/,
    use: ['file-loader'],
  },
)

config.plugins.push(new MonacoWebpackPlugin())
