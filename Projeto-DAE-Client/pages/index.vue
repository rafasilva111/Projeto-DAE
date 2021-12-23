<template>
 <!-- easy components usage, already shipped with bootstrap css-->
 <b-container fluid>



 <!-- try to remove :fields=”fields” to see the magic -->


 </b-container>
</template>
<script>
export default {
 data () {
 return {
 fields: ['name', 'value', 'date','utilizadorNormalID'],
 students: []
 }
 },
 created () {
 this.$axios.$get('/api/biosinais/colestrol')
 .then((students) => {
 this.students = students
 })
 }
}
</script>
<style></style>
