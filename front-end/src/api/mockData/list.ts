import Mock from "mockjs";

const { Random } = Mock;

const data = Mock.mock({
  "list|55": [
    {
      "id|8": /[A-Z][a-z][-][0-9]/,
      "number|2-3": /[0-9]/,
      "name|4-8": /[A-Z]/,
      "eventType|1": ["img", "horizontalVideo", "verticalVideo"],
      "count|2-3": /[0-9]/,
      "status|1": ["online", "offline"],
      "filterType|1": ["artificial", "rules"],
      createdTime: Random.datetime(),
    },
  ],
});

export default {
  getEventList: (config: any) => {
    // 后端分页
    const { current = 1, pageSize = 10 } = JSON.parse(config.body);
    const p = current as number;
    const ps = pageSize as number;
    return {
      code: 200,
      data: {
        list: data.list.slice((p - 1) * ps, p * ps),
        total: 55,
      },
    };
  },
};

// setupMock({
//   setup() {
//     Mock.mock(new RegExp('/api/list/policy'), (params: GetParams) => {
//       const { current = 1, pageSize = 10 } = qs.parseUrl(params.url).query;
//       const p = current as number;
//       const ps = pageSize as number;
//       return successResponseWrap({
//         list: data.list.slice((p - 1) * ps, p * ps),
//         total: 55,
//       });
//     });
//   },
// });
