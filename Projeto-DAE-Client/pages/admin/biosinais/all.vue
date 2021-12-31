<template>
  <div class="pt-4">
    <div>
      <b-container class="modal-content rounded-6 shadow p-4" >
        <h1 class="p-2" style="text-align:center">Biosinais Criados</h1>
        <b-table striped  :items="outro" :fields="fields"  style="float:left;">
          <template v-slot:cell(actions)="row">
            <nuxt-link
              class="btn btn-dark btn-sm"
              :to="`/admin/biosinais/${row.item.id}/update`">Atualizar</nuxt-link>
            <button
              class="btn btn-danger btn-sm"
              @click="apagar(row.item.id)">Apagar</button>


          </template>
        </b-table>
        <b-row >
          <b-col lg="6" class="pb-4"><nuxt-link   class="btn btn-dark btn-sm" to="/admin/biosinais/create"  >Inserir Registo</nuxt-link></b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      graphData: [],
      ready: false,
      labels: [1,2,3],
      data: [4,3,2],
      fields: [
        {
          key: 'date',
          label: 'Data',
        },
        {
          key: 'name',
          label: 'Nome',
        },
        {
          key: 'maxValues',
          label: 'Valor máximo',
        },
        {
          key: 'minValues',
          label: 'Valor mínimo',
        },
        {
          key: 'actions',
          label: '',

        }
      ],
      outro: [{"date" : "12","height" : "25"+" Cm","weight": "25" +" Kg", "classification" : "123","descricao" : "653"}],
      user: null,


    }
  },
  created () {


        this.$axios.$get('/api/admin/outro/')
          .then((outro) => {
this.outro = outro;

          })

  },

  methods: {
    apagar: function (value){

      this.$axios.$delete('/api/biosinais/pesagem/'+value)
        .then(() => {
          this.$axios.$get('/api/biosinais/pesagem/'+this.user.id)
            .then((colestrol) => {
              this.colestrol = colestrol
            })


        })
    },
    IMCcomputed: function (altura,peso) {
      // `this` points to the vm instance
      console.log(altura+" "+peso);
      return peso*(altura*altura);
    },

  },
  computed: {
    // a computed getter

  }
}
</script>
