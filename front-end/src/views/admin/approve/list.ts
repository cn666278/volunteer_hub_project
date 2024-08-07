import axios from 'axios';
// import qs from 'query-string';
import type { DescData } from '@arco-design/web-vue/es/descriptions/interface';

export interface PolicyRecord {
  id: string;
  number: number;
  title: string;  // 修改 name 为 title
  description: string;  // 新增字段
  location: string;  // 新增字段
  pointsAwarded: number;  // 新增字段
  eventType: 'Judo' | 'Badminton' | 'Table Tennis' | 'Hockey' | 'Cycling' | 'Snowsports';
  filterType: 'artificial' | 'rules';
  count: number;
  status: 'Awaiting Review' | 'Passed';
  startDate: string;  // 修改 createdTime 为 startDate
  endDate: string;  // 新增字段
}

export interface PolicyParams extends Partial<PolicyRecord> {
  current: number;
  pageSize: number;
}

export interface PolicyListRes {
  list: PolicyRecord[];
  total: number;
}

// 获取事件列表的方法
// export function queryEventList(params: PolicyParams) {
//   return axios.get<PolicyListRes>('/api/list/event', {
//     params,
//     paramsSerializer: (obj) => {
//       return qs.stringify(obj);
//     },
//   });
// }

export interface ServiceRecord {
  id: number;
  title: string;
  description: string;
  name?: string;
  actionType?: string;
  icon?: string;
  data?: DescData[];
  enable?: boolean;
  expires?: boolean;
}

export function queryInspectionList() {
  return axios.get('/api/list/quality-inspection');
}

export function queryTheServiceList() {
  return axios.get('/api/list/the-service');
}

export function queryRulesPresetList() {
  return axios.get('/api/list/rules-preset');
}
