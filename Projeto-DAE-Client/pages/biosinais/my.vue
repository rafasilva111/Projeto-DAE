<template>
  <div margin="50px" >
  <div class="modal-content rounded-6 shadow" margin-top="100px">
    <caption style="text-align:center">Colestrol</caption>
    <b-table striped  :items="colestrol" :fields="fields" title="Colestrol" />
  </div>

  </div>
</template>

<script>

export default {
  data () {
    return {
      fields: ['value', 'date','classification'],
      colestrol: [],
      bpm: [],
      pesagem: [],
      outro: [],

    }
  },

  created () {
    console.log(this.$auth.user)
    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers', {
      headers: {
        'Authorization': `Bearer `
      }
    })
      .then((students) => {
        this.students = students
      })
  }
}
</script>

<style scoped>

</style>
