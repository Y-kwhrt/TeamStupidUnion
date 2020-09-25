
## th:field
```
<input type="text" th:field="*{type}">
```
↓
```
<input type="text" name="type" id="type">
```

## th:errorclass
```
<input type="text" th:errorclass="error-input">
```
↓
- 正常時
```
<input type="text">
```
- エラー時
```
<input type="text" class="error-input">
```
