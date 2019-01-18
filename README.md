
## 描述 ##

使用 [relight](https://github.com/ittianyu/relight) 开发的支持实时动态更新的 todo app。

## 业务需求 ##

#### 第一版计划 ####

- 支持本地存储
- 添加 item
- 编辑 item
- 删除 item
- 标记 item 完成
- 移动 item
- 支持多维度排序

#### 第二版计划 ####

- 支持网络存储
- 支持自搭建服务

## 技术需求 ##

- 支持页面动态更新
- 统计动态更新成功率

## 进度 ##

- [x] 项目搭建
- [x] 路由定义
- [x] 数据源定义
- [x] 本地数据源实现
- [x] splash 页面
- [x] todo list 页面
- [x] 添加 todo 页面
- [x] 编辑 todo 页面
- [ ] 删除 item 支持
- [ ] todo 排序
- [ ] 关于 页面
- [ ] 支持页面动态更新
- [ ] 埋点统计动态更新成功率
- [ ] 添加网络数据源
- [ ] 支持自定义服务地址


## 数据定义 ##

#### 任务表:task ####

- id long
- title varchar(64)	index
- description text
- start_time long	index
- end_time long		index
- status tinyint 	[0:waiting 1:doing 2:completed]
- priority tinyint	[0:low 1:normal 2:high 3:urgent]
- create_time long	index

#### 标签表:tag ####

- id long
- task_id long
- name varchar(32) index

