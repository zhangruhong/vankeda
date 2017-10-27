在 310-314行 增加如下代码（作用：在页码前增加 “当前页开始-当前页结束 / 总数” [ 如：1-10 / 125 ]）
//update pageInfo
var startnumber = (this.options.total == 0) ? 0 : this.options.currentPage * this.options.pageSize - this.options.pageSize + 1;
var endnumber = (this.options.currentPage == this.options.totalPages) ? this.options.total : this.options.currentPage * this.options.pageSize;
var numbers = '' + startnumber + '-' + endnumber;
listContainer.append('<li style="float: left;line-height: 1;padding: 7px 0 7px 0;margin: 0 5px 0 0;color: #aaa;font-size: 12px;">'+ numbers +' / '+ this.options.total+'</li>');         