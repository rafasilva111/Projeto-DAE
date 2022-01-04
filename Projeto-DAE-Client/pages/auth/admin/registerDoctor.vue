<template>
  <b-container>
    <h3 style="text-align: center">Doctors Management</h3>
    <h3 style="text-align: center" class="pb-4">Create Form</h3>
    <b-container class="modal-content rounded-6 shadow p-4">
    <b-form @submit.prevent="onSubmit" @reset="onReset">
      <b-form-group label="Username" description="Enter your desired username">
        <b-input
          name="username"
          placeholder="Insert Username"
          v-model.trim="username"
          required />
      </b-form-group>
      <b-form-group label="Password" description="Enter your desired password">
        <b-input
          name="password"
          type="password"
          placeholder="Insert Password"
          v-model="password"
          required />
      </b-form-group>
      <b-form-group label="Email" description="Enter your desired email">
        <b-input
          name="email"
          type="email"
          placeholder="Insert Email"
          v-model="email"
          required />
      </b-form-group>
      <b-button type="reset" class="btn-warning">Reset</b-button>
      <b-button type="submit" class="btn-success">Submit</b-button>
    </b-form>
      </b-container>
  </b-container>
</template>
<script>
export default {
  auth: false,
  data() {
    return {
      username: null,
      password: null,
      email:null,
      medicos:[],
      selected:null,
    }
  },
  created () {
        this.$axios.$get('/api/user/alldocs/')
          .then((medicos) => {
            this.medicos = medicos
            console.log(medicos);
          })
  },
  methods: {
    create() {
      this.$axios.$post('/api/user/new/doctor/create', {
        username : this.username,

        password : this.password,

        email : this.email,

      })
        .then(() => {
          console.log(this.username,this.password,this.email)
          this.$router.push('/listings/doutores')
        })
        .catch(error => {
          this.errorMsg = error.response.data
        })
    },

    onSubmit() {
      this.create()
    },
    onReset() {
      this.username = null
      this.password = null
    }
  }
}
</script>
