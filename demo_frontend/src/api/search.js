import request from '@/utils/request'

// 关键词检索
export function searchByKeyword(query) {
  return request({
    url: `/search`,
    method: 'get',
    params: {
      keyword: query.keyword,
    }
  })
}