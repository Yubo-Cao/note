---
date created: 2022-05-23 12:02
date updated: 2022-05-24 12:46
---

[[Internet Basic, HTTP(s), TCP, and UDP]]

# 网页基础

## 网页的组成

- HTML, CSS, 和 JavaScript 组成了网页

### HTML 内容和结构

- HTML 是描述网页的语言，又成为 Hyper Text Markup Language, 超文本标记语言。
- 各种标签嵌套在一起，组成了网页。这个东西定义了网页的架构。

### CSS 布局

- 只有 HTML 的布局并不美观，因为只不过是简单的节点元素从上到下的排列。CSS 可以美化网页，突出重点，进行更好的布局。
- CSS，称为 Cascading Style Sheets, 即层叠样式表。
  - 层叠 = 如果 HTML 元素使用了多个样式文件，并且冲突时，浏览器能高按照层叠顺序处理。
  - 样式 = 文字的排版，元素的颜色，间距，排列等等。
- CSS 包含
  - CSS 选择器
  - 内部包含样式规则
  - 然后使用大括号包含起来
- 大多数时候， HTML 直接通过 `<link href=xx>` 来引入样式文件，然后就完事了。

##### CSS 选择器

| 选择器                   | 作用                                  |
| --------------------- | ----------------------------------- |
| `.class`              | 选择所有 `class` 属性中包含　`class` 的节点      |
| `#id`                 | 选择 `id="id"` 节点                     |
| `*`                   | 选择所有节点                              |
| `element`             | 选择所有 `element` 节点                   |
| `element,element2`    | 选择所有 `element` 或者 `element2` 节点     |
| `outer inner`         | 选择所有 `outer` 节点内部的所有 `inner` 节点     |
| `parent>child`        | 选择父节点为 `parent` 节点的所有 `child` 节点    |
| `previous-sb>next-sb` | 紧接在`previous-sb` 之后的所有 `next-sb` 节点 |
| `[attr]`              | 选择带有 `attr` 属性的节点                   |
| `[attr=val]`          | 选择所有带有 `attr` 属性，并且该属性的值为 `val` 的节点 |
| `[attr~=val]`         | 带有 `attr` 属性中包含 `val` 的节点           |
| `:not(selector)`      | 选择不被 `selector` 选中的节点               |
| `::selection`         | 选择被用户选中的节点                          |

除了上述提供的这些节点，还有一种选择器，称为 `XPath`，也总是被适用。

### JS 行为

- 提供交互和动画效果，甚至动态的修改 HTML 中的内容。
- 实时，动态，交互的页面功能。
- 通过 `<script src=xx></script>` 的方式引入。
